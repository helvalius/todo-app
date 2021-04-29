package io.github.helvalius

import io.github.helvalius.model.dao.Todo
import io.github.helvalius.model.repository.TodoRepository
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.mapper.ObjectMapperType
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import javax.inject.Inject


@Testcontainers
@TransactionalQuarkusTest
@QuarkusTest
class TodoResourceIntegrationTest {
    companion object {
        @Container
        private val db = PostgreSQLContainer<Nothing>("postgres:12").apply {
            withDatabaseName("quarkusjdbc")
            withUsername("quarkus")
            withPassword("changeme")
        }

        @BeforeAll
        @JvmStatic
        fun setProps() {
            db.waitingFor(HostPortWaitStrategy())
            db.start()
            System.setProperty("%test.quarkus.datasource.driver", db.driverClassName)
            System.clearProperty("%test.quarkus.hibernate-orm.dialect")
            System.setProperty("%test.quarkus.datasource.url", db.jdbcUrl)
            System.setProperty("%test.quarkus.datasource.username", db.username)
            System.setProperty("%test.quarkus.datasource.password", db.password)
        }

        @AfterAll
        @JvmStatic
        fun removeProps() {
            System.setProperty("%test.quarkus.datasource.driver", "org.testcontainers.jdbc.ContainerDatabaseDriver")
            System.setProperty("%test.quarkus.hibernate-orm.dialect", "org.hibernate.dialect.PostgreSQL10Dialect")
            System.setProperty("%test.quarkus.datasource.url", "jdbc:tc:postgresql:latest:///dbname")
            System.clearProperty("%test.quarkus.datasource.username")
            System.clearProperty("%test.quarkus.datasource.password")
            if (db.isCreated) {
                db.close()
            }
        }
    }


    @Inject
    @field: javax.enterprise.inject.Default
    lateinit var todoRepository: TodoRepository

    @BeforeEach
    fun clearDatabase() {todoRepository.deleteAll()}


    @Test
    fun testPostEndpoint() {
        val result = given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(
                """{ "name": "Test123", "description": "Fancy description", "tasks":[{"name":"Subtask"}]}"""
            )
            .`when`().post("/todos")
            .then()
            .assertThat()
            .extract().`as`(Todo::class.java, ObjectMapperType.JACKSON_2)

        assertThat(result.name, equalTo("Test123"))
        assertThat(result.description, equalTo("Fancy description"))
        assertThat(result.tasks.size, equalTo(1))
        assertThat(result.id, notNullValue())
        assertThat(result.id, not(equalTo(0)))
    }

}
