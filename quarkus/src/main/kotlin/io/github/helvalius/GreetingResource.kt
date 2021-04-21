package io.github.helvalius

import io.github.helvalius.model.resource.CreateTodoResource
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/todos")
class TodoResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun hello() = "Hello RESTEasy"

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun retrieveTodo(@PathParam("id") id: Long) = "Hello RESTEasy"

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    fun createNewTodo(@Valid newTodo: CreateTodoResource) = "Hello RESTEasy"
}
