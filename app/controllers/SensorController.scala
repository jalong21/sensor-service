package controllers

import models.Sensor
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc._

import javax.inject.Inject
import scala.util.{Failure, Success, Try}


class SensorController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  val log = Logger(this.getClass.getName)

  def sensor() = Action {
    request => request.body.asJson
      .map( requestBody => {
        log.warn(s"requestBody: ${requestBody.toString()}")
        Try(Json.fromJson[Sensor](requestBody).get) match {
          case Success(sensor) => {
            log.warn(s"received sensor. key: ${sensor.key}, value: ${sensor.value}")
            Ok(sensor.key.toString)
          }
          case Failure(ex) => BadRequest(s"Error Parsing Request Body! ${ex.getMessage}")
        }
      }).getOrElse(BadRequest("Payload Missing From Request"))
  }
}