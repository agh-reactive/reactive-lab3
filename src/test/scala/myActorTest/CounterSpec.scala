package myActorTest

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike
import akka.actor.testkit.typed.scaladsl.BehaviorTestKit
import akka.actor.testkit.typed.scaladsl.TestInbox

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

    "increment the value (synchronous testing version)" in {
      import Counter._
      val counter = BehaviorTestKit(Counter(0))
      val mainInbox   = TestInbox[CounterMain.Command]()
      counter.run(Incr)
      counter.run(Get(mainInbox.ref))
      mainInbox.expectMessage(CounterMain.Count(1))
    }

  }

}
