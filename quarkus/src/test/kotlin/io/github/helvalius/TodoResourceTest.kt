package io.github.helvalius

import io.github.helvalius.model.dao.Todo
import io.github.helvalius.model.resource.CreateTaskDto
import io.github.helvalius.model.resource.CreateTodoDto
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers


@Testcontainers
@QuarkusTest
class TodoResourceIntegrationTest {
    companion object {
        @Container
        val container = PostgreSQLContainer<Nothing>("postgres:12").apply {
            withDatabaseName("testdb")
            withUsername("test")
            withPassword("s3crEt")
        }
    }

    @Test
    fun testPostEndpoint() {
        val result = given()
            .contentType(ContentType.JSON)
            .body(
                """{ "name": "Test", tasks =[{"name":"Subtask"}]}"""
            )
            .`when`().post("/todos")
            .then()
            .statusCode(200)
            .extract().body().jsonPath()
    }

}
