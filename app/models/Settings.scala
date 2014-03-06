package models

import play.api._

object Settings {
  def outputDir: String =
    Play.current.configuration.getString("output.dir", None).getOrElse("./public/generated")
}