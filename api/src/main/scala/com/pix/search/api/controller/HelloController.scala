package com.pix.search.api.controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class HelloController extends Controller {

  get("/hello") { request: Request =>
    "Pix Search"
  }
}
