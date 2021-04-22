package io.github.helvalius

import io.github.helvalius.model.resource.CreateTodoResource
import io.github.helvalius.model.resource.UpdateTodoResource
import io.github.helvalius.service.TodoService
import org.jboss.resteasy.annotations.Body
import javax.inject.Inject
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
class TodoResource {
    @Inject
    lateinit var todoService: TodoService

    @GET
    fun retrieveAll() = todoService.getAll()

    @GET
    @Path("{id}")
    fun retrieveTodo(@PathParam("id") id: Long) = todoService.getOne(id)

    @PUT
    @Path("{id}")
    fun updateTodo(@PathParam("id") id: Long, @Valid updateTodo: UpdateTodoResource) = todoService.update(id, updateTodo)

    @DELETE
    @Path("{id}")
    fun deleteTodo(@PathParam("id") id: Long) = todoService.delete(id)

    // TODO: throw exception if create failed or wrap exception to something someone can handle with an advice
    @POST
    fun createNewTodo(@Valid newTodo: CreateTodoResource) = todoService.createNewTodo(newTodo)
}
