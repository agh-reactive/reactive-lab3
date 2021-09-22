package myActorTest

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

import scala.concurrent.Await
import scala.concurrent.duration._

object ToggleActor {

  trait Command
  case class HowAreYou(relyTo: ActorRef[String]) extends Command
  case class Done(relyTo: ActorRef[String])      extends Command

  def happy: Behavior[Command] = Behaviors.receiveMessage {
    case HowAreYou(replyTo) =>
      replyTo ! "happy"
      sad

    case Done(replyTo) =>
      replyTo ! "Done"
      Behaviors.stopped
  }

  def sad: Behavior[Command] = Behaviors.receiveMessage {
    case HowAreYou(replyTo) =>
      replyTo ! "sad"
      happy

    case Done(replyTo) =>
      replyTo ! "Done"
      Behaviors.stopped
  }
  def apply(): Behavior[Command] = happy
}

object ToggleMain {

  def apply(): Behavior[String] = Behaviors.setup { context =>
    apply(context.spawn(ToggleActor(), "toggle").ref)
  }

  def apply(toggle: ActorRef[ToggleActor.Command]): Behavior[String] =
    Behaviors.receive(
      (context, msg) =>
        msg match {
          case "Init" =>
            toggle ! ToggleActor.HowAreYou(context.self)
            toggle ! ToggleActor.HowAreYou(context.self)
            toggle ! ToggleActor.HowAreYou(context.self)
            toggle ! ToggleActor.Done(context.self)
            Behaviors.same
          case "Done" =>
            context.system.terminate
            Behaviors.stopped
          case msg: String =>
            println(s" received: $msg")
            Behaviors.same
      }
    )
}

object ToggleApp extends App {
  val system: ActorSystem[String] = ActorSystem(ToggleMain(), "Reactive2")

  system ! "Init"

  Await.result(system.whenTerminated, Duration.Inf)
}
