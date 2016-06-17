package controller

import skinny.http._
import skinny.micro.response.Ok

import scala.xml.{NodeSeq, XML}

class XMLController extends ApplicationController {
  def index = {
    // リクエスト用のXML
    val request = <request>
      <user_id>USER_ID</user_id>
      <password>PASSWORD</password>
    </request>

    // 認証用URLにXMLをPOSTする
    val auth: Response = HTTP.request(Method.POST, Request("http://localhost:8080/auth").body(request.text.getBytes).contentType("application/xml"))

    // Set-Cookieにマルチバリューで認証情報が入っているので;で連結する
    val cookies = auth.headerField("Set-Cookie").map(_.split(";")(0)).mkString(";")
    // Cookieに認証情報を入れてAPIをGETする
    val response: Response = HTTP.request(Method.GET, Request("http://localhost:8080/product").header("Cookie", cookies))

    // レスポンスをスクリプティングする
    val xml = XML.loadString(response.textBody)
    val name: NodeSeq = xml \\ "@name"

    Ok(name.text, contentType = Some("application/xml"))
  }
}
