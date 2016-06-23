package controller

import skinny.micro.response.Ok
import util.WebResource

import scala.xml.{NodeSeq, XML}

class WebController extends ApplicationController {
  def index = {
    // https://www.jomalone.jp/whats-new
    val web = WebResource("https://www.jomalone.jp/whats-new")
    println(web.summary)

    Ok(<html>hoge</html>, contentType = Some("application/xml"))
  }
}
