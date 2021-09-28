package myActorTest

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

class ToggleSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {

  import ToggleActor._

  "A Toggle" must {

    "start in a happy mood" in {
      val toggle = testKit.spawn(ToggleActor())
      val probe  = testKit.createTestProbe[String]()

      toggle ! HowAreYou(probe.ref)
      probe.expectMessage("happy")
    }

    "change its mood" in {
      val toggle = testKit.spawn(ToggleActor())
      val probe  = testKit.createTestProbe[String]()

      for (i <- 1 to 5) {
        toggle ! HowAreYou(probe.ref)
        probe.expectMessage("happy")
        toggle ! HowAreYou(probe.ref)
        probe.expectMessage("sad")
      }
    }

    "finish when done" in {
      val toggle = testKit.spawn(ToggleActor())
      val probe  = testKit.createTestProbe[String]()

      toggle ! Done(probe.ref)
      probe.expectMessage("Done")
    }

  }

}
