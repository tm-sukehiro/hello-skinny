package controller

import skinny.micro.response.{Created, Ok}

class ApiUsersController extends ApiController {
  def get = {
    Ok()
  }

  def create = {
    val isCreated = true
    if (isCreated) Created() else Ok()
  }
}
