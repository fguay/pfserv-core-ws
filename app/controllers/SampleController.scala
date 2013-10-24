package controllers

import fr.canal.vod.sample.api.dto.Sample
import play.api._
import play.api.mvc._

import akka.actor.{ActorSystem, TypedProps, TypedActor}
import fr.canal.vod.sample.api.akka.SampleActor
import play.libs.Akka
import play.api.libs.concurrent.Execution.Implicits._
import com.typesafe.config.ConfigFactory
import scala.concurrent.Future


object SampleController extends Controller{

  val system = ActorSystem("sample",  ConfigFactory.load().getConfig("sample"));
  val sampleActor: SampleActor = TypedActor(system).typedActorOf(TypedProps[SampleActor],system.actorFor("akka.tcp://sample@127.0.0.1:2553/user/sample"))

  def sample(sample : String) = Action {
    Async {
      val f1 = sampleActor.jpaSample(sample + "-jpa")
      val f2 = sampleActor.docSample(sample + "-doc")
      for {
         s1 <- f1.mapTo[Sample]
         s2 <- f2.mapTo[Sample]
      } yield Ok(s1.getName() + " - " + s2.getName())
    }
  }

}
