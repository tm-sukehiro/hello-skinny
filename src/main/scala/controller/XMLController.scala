package controller

import skinny.http._
import skinny.micro.response.Ok

import scala.xml.{NodeSeq, XML}

class XMLController extends ApplicationController {
  def index = {
    val response: Response = HTTP.get("http://d.hatena.ne.jp/changes.xml")

    val xml = XML.loadString(response.textBody)
    val a: NodeSeq = xml \\ "@name"

    response.headers.foreach(t => println(t))

    Ok(response.textBody, contentType = Some("application/xml"))
  }
}
