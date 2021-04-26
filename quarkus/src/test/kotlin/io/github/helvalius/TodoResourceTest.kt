package io.github.helvalius

import io.github.helvalius.model.dao.Todo
import io.github.helvalius.model.resource.CreateTaskDto
import io.github.helvalius.model.resource.CreateTodoDto
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.mapper.ObjectMapperType
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
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
            .accept(ContentType.JSON)
            .body(
                """{ "name": "Test", "description": "Fancy description", "tasks":[{"name":"Subtask"}]}"""
            )
            .`when`().post("/todos")
            .then()
            .assertThat()
            .extract().`as`(Todo::class.java, ObjectMapperType.JACKSON_2)

        assertThat(result.name, equalTo("Test"))
        assertThat(result.description, equalTo("Fancy description"))
        assertThat(result.tasks.size, equalTo(1))
        assertThat(result.id, notNullValue())
        assertThat(result.id, not(equalTo(0)))
    }

}
