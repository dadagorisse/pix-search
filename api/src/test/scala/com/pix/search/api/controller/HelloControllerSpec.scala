package com.pix.search.api.controller

import com.pix.search.api.app.PixSearchServer
import com.twitter.finagle.http.Status
import com.twitter.finatra.http.test.{EmbeddedHttpServer, HttpTest}
import com.twitter.inject.server.FeatureTest

class HelloControllerSpec extends FeatureTest with HttpTest {

  override val server = new EmbeddedHttpServer(new PixSearchServer, disableTestLogging = true)

  "Get" should {
    "say 'Pix Search" in {
      server.httpGet(
        path = "/hello",
        andExpect = Status.Ok,
        withBody = "Pix Search")
    }
  }
}
