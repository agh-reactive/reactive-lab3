package myActorTest
import akka.testkit.TestKit
import akka.actor.ActorSystem
import org.scalatest.WordSpecLike
import scala.concurrent.Future
import java.util.concurrent.Executor
import org.scalatest.BeforeAndAfterAll
import akka.testkit.ImplicitSender
import akka.actor.Props
import akka.actor.Actor
import akka.actor.ActorRef
import akka.testkit.TestActorRef


class CounterSpec extends TestKit(ActorSystem("CounterSpec"))
  with WordSpecLike with BeforeAndAfterAll with ImplicitSender {
  
  
  override def afterAll(): Unit = {
    system.terminate
  }

  "A Counter" must {

    "increment the value" in {
      import Counter._
      
      val counter = TestActorRef[Counter]
    
      counter ! Incr
      
      assert (counter.underlyingActor.count == 1)
      
      
    }
    
    
 
  }

}

