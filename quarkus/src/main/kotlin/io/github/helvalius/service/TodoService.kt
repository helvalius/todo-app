package io.github.helvalius.service

import io.github.helvalius.model.dao.Task
import io.github.helvalius.model.dao.Todo
import io.github.helvalius.model.repository.TodoRepository
import io.github.helvalius.model.resource.CreateTodoResource
import mu.KotlinLogging
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional


/**
 * Date: 21.04.21<br>
 * Author: Jan Nonnen
 * </p>
 */
@ApplicationScoped
class TodoService {
    private val logger = KotlinLogging.logger {}

    @Inject
    lateinit var repository: TodoRepository

    @Transactional
    fun createNewTodo(todoResource: CreateTodoResource): Todo {  // TODO change return to transfer class
        val todo = Todo(
            name = todoResource.name,
            description = todoResource.description,
            tasks = todoResource.tasks.map {
                Task(name = it.name, description = it.description)
            }
        )
        logger.info("Created new todo {$todo}")
        repository.persist(todo)
        return todo
    }

    fun getOne(id: Long) : Todo =   repository.findById(id)   // TODO change return to transfer class

    @Transactional
    fun delete(id:Long) = repository.deleteById(id)


}
