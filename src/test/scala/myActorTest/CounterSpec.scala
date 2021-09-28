package myActorTest

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

class CounterSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {

  "A Counter" must {

    "increment the value" in {
      import Counter._
      val counter = testKit.spawn(Counter(0))
      val probe   = testKit.createTestProbe[CounterMain.Command]()
      counter ! Incr
      counter ! Get(probe.ref)
      probe.expectMessage(CounterMain.Count(1))
    }

  }

}
