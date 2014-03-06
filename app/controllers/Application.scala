package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import au.com.bytecode.opencsv._
import models._
import org.apache.commons.codec.digest.DigestUtils

/** Application controller, handles authentication */
object Application extends Controller with Secured {

  /** Serves the index page, see views/index.scala.html */
  def index = Action {
    Ok(views.html.login())
  }

  def uploadForm = Action {
    Ok(views.html.upload())
  }

  def upload = Action(parse.multipartFormData) { request =>
    request.body.file("picture").map { picture =>
      import java.io._
      val filename = picture.filename 
      val contentType = picture.contentType
      // TODO: move to model
      val reader = new InputStreamReader(new FileInputStream(picture.ref.file), "cp1251")
      val csvReader = new CSVReader(reader, ';','"')
      import scala.collection.JavaConversions._
      val lines = csvReader.readAll().drop(8).dropRight(5)
      HtmlGeneration.rest(lines.iterator,"fizrastvor")
      //  ID of user, who uploaded file will be here"
      Ok(views.html.upload())
    }.getOrElse {
      Redirect("login").flashing(
        "error" -> "Missing file"
      )
    }
  }

  case class AuthInfo(login:String, password:String)
  implicit val authInfoReads = Json.reads[AuthInfo]
  implicit val authInfoWrites = Json.writes[AuthInfo]

  val availableUsers = Map("admin1" -> "0b14d501a594442a01c6859541bcb3e8164d183d32937b851835442f69d5c94e")

  def login() = Action( Ok(views.html.login()) )
  /**
   * Log-in a user. Pass the credentials as JSON body.
   * @return The token needed for subsequent requests
   */
  def auth() = Action(parse.json) { implicit request =>
    val session = for(auth <- Json.fromJson[AuthInfo](request.body) if availableUsers.get(auth.login) == DigestUtils.sha256(auth.password)){
        request.session + ("user"+auth.login)
    }
    // TODO Check credentials, log user in, return correct token
    val token = java.util.UUID.randomUUID().toString
    Ok(Json.obj("token" -> token))
  }


  /** Logs the user out, i.e. invalidated the token. */
  def logout() = Action {
    // TODO Invalidate token, remove cookie
    Ok
  }

}
