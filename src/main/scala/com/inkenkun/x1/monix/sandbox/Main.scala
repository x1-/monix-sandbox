package com.inkenkun.x1.monix.sandbox

import cats.syntax.either._
import cats.syntax.traverse._
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

object Main {
  def main(args: Array[String]): Unit = {
    println(MonixPlayGround.play)
  }
}

  trait TaskExecutor {
    def try2eitherTask[A](t: Try[A]): Task[Either[Throwable, A]] = t match {
      case Success(a) => Task.now(Either.right(a))
      case Failure(e) => Task.now(Either.left(e))
    }
  }

object MonixPlayGround extends MonixPlayGround
trait MonixPlayGround extends TaskExecutor {

  def play: Either[String, Seq[Int]] = {
    val sequence = Task.gatherUnordered((1 to 10).map(x => task(x)))
    val future = sequence.runAsync
    val pairs: Seq[Either[Throwable, Int]] = Await.result(future, 30 millis)

    val errors = pairs.collect {
      case Left(x) => x.getMessage
    } mkString "\n"

    val xs = pairs.collect {
      case Right(x) => x
    }

    if (xs.isEmpty) Either.left(errors)
    else Either.right(xs)
  }

  def task(i: Int): Task[Either[Throwable, Int]] = {
    val task = Task {
      if (i % 2 == 1) {
        Thread.sleep(1000)
      }
      i
    }
      .timeout(20 millis)
      .materialize

    task.flatMap(try2eitherTask)
  }

}
