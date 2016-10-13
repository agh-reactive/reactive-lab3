package myActorTest

import akka.actor._
import scala.concurrent.duration._
import scala.concurrent.Await

object Counter{
  case object Incr
  case object Get
}

class Counter extends Actor {
  import Counter._
  var count = 0
  def receive = {
    case Incr => count += 1; println("Thread name: "+Thread.currentThread.getName + ".")
    case Get  => sender ! count // "!" operator is pronounced "tell" in Akka
  }
}
 


class CounterMain extends Actor {
  
  def receive = {
    case "init" =>
      val counter = context.actorOf(Props[Counter], "counter")
      counter ! Counter.Incr
      counter ! Counter.Incr
      counter ! Counter.Incr
      counter ! Counter.Get
     
    case count: Int =>
      println(s"count received: $count" )
      println(Thread.currentThread.getName + ".")
      context.system.terminate
  }
}


object ApplicationMain extends App {
  val system = ActorSystem("Reactive1")
  val mainActor = system.actorOf(Props[CounterMain], "mainActor")

  mainActor ! "init"

  Await.result(system.whenTerminated, Duration.Inf)
}
