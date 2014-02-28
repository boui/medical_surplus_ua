package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import au.com.bytecode.opencsv._
import models._

/** Application controller, handles authentication */
object Application extends Controller with Secured {

  /** Serves the index page, see views/index.scala.html */
  def index = Action {
    Ok(views.html.index())
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
      HtmlGeneration.rest(lines.iterator(),"fizrastvor")
      //  ID of user, who uploaded file will be here"
      Ok(views.html.upload())
    }.getOrElse {
      Redirect(routes.Application.index).flashing(
        "error" -> "Missing file"
      )
    }
  }


  /**
   * Returns the JavaScript router that the client can use for "type-safe" routes.
   * @param varName The name of the global variable, defaults to `jsRoutes`
   */
  def jsRoutes(varName: String = "jsRoutes") = Action { implicit request =>
    Ok(
      Routes.javascriptRouter(varName)(
        routes.javascript.Application.auth
        // TODO Add your routes here
      )
    ).as(JAVASCRIPT)
  }

  case class AuthInfo(login:String, password:String);

  def login() = Action( Ok(views.html.login()) )
  /**
   * Log-in a user. Pass the credentials as JSON body.
   * @return The token needed for subsequent requests
   */
  def auth() = Action(parse.json) { implicit request =>
    val (login, password) = request.body.as[AuthInfo]

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
