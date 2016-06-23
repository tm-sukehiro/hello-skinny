package util

import java.io.{StringReader, StringWriter}

import org.apache.html.dom.HTMLDocumentImpl
import org.apache.xml.serialize.{OutputFormat, XMLSerializer}
import org.cyberneko.html.parsers.DOMFragmentParser
import org.w3c.dom._
import org.xml.sax.InputSource

import scala.xml._
import skinny.http._

case class WebResource (url: String, status: Int, title: Option[String], summary: Option[String]) {

  def exists: Boolean = List(200, 301, 302).contains(status)

}

object WebResource {

  def apply(url: String) = {
    val response = HTTP.get(url)
    val statusCode = response.status
    if (List(200, 301, 302).contains(statusCode)) {
      val html = convertHtmlToXml(response.textBody)
      new WebResource(url, statusCode, None, Some(getSummary(html)))
    } else {
      new WebResource(url, statusCode, None, None)
    }

  }

  private def getSummary(html: Elem): String = {
    (html \ "BODY" \\ "P").headOption.map(_.text.replaceAll("[ \n\t　]+", " ").take(156) + "...").getOrElse("")
  }

  private def convertHtmlToXml(html: String): Elem = XML.loadString(modify(removeBOM(html)))

  private def removeBOM(str: String) = if (str.head == 0xFEFF.asInstanceOf[Char]) {
    str.tail
  } else {
    str
  }

  private def modify(html: String): String = {
    val parser: DOMFragmentParser = new DOMFragmentParser
    val fragment: DocumentFragment = new HTMLDocumentImpl().createDocumentFragment
    val src: InputSource = new InputSource(new StringReader(html))

    parser.parse(src, fragment)

    val writer: StringWriter = new StringWriter
    val format: OutputFormat = new OutputFormat

    format.setOmitXMLDeclaration(true)
    format.setOmitDocumentType(true)
    format.setOmitComments(true)

    val serializer: XMLSerializer = new XMLSerializer()

    serializer.setOutputCharStream(writer)
    serializer.setOutputFormat(format)
    serializer.serialize(fragment)
    writer.getBuffer.toString
  }
}
