package myActorTest

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}

import scala.concurrent.duration._
import scala.concurrent.Await

object Counter {
  trait Command
  case object Incr                                       extends Command
  case class Get(replyTo: ActorRef[CounterMain.Command]) extends Command

  def apply(count: Int): Behavior[Command] = Behaviors.receiveMessage {
    case Incr =>
      println("Thread name: " + Thread.currentThread.getName + ".")
      apply(count + 1)
    case Get(replyTo) =>
      replyTo ! CounterMain.Count(count) // "!" operator is pronounced "tell" in Akka
      Behaviors.same
  }
}

object CounterMain {
  trait Command
  case object Init             extends Command
  case class Count(count: Int) extends Command

  def apply(): Behavior[Command] =
    Behaviors.receive(
      (context, msg) =>
        msg match {
          case Init =>
            val counter = context.spawn(Counter(0), "counter")
            counter ! Counter.Incr
            counter ! Counter.Incr
            counter ! Counter.Incr
            counter ! Counter.Get(context.self)
            Behaviors.same
          case Count(count) =>
            println(s"count received: $count")
            println(Thread.currentThread.getName + ".")
            context.system.terminate
            Behaviors.stopped
      }
    )
}

object ApplicationCounter extends App {
  val system = ActorSystem(CounterMain(), "mainActor")

  system ! CounterMain.Init

  Await.result(system.whenTerminated, Duration.Inf)
}
