package io.github.helvalius

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class TodoResourceTest {

    @Test
    fun testHelloEndpoint() {
        given()
          .`when`().get("/todos")
          .then()
             .statusCode(200)
             .body(`is`("Hello RESTEasy"))
    }

}