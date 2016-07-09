package controller

import skinny._
import skinny.controller.AssetsController

object Controllers {

  def mount(ctx: ServletContext): Unit = {
    web.mount(ctx)
    xml.mount(ctx)
    members.mount(ctx)
    root.mount(ctx)
    AssetsController.mount(ctx)
  }

  object root extends RootController with Routes {
    val indexUrl = get("/?")(index).as('index)
  }

  object members extends _root_.controller.MembersController with Routes {
  }

  object xml extends XMLController with Routes {
    get("/xml/?")(index)
    get("/main/?")(main)
  }

  object web extends WebController with Routes {
    get("/web/?")(index)
  }

}
