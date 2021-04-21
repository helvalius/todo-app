package io.github.helvalius.service

import io.github.helvalius.model.dao.Task
import io.github.helvalius.model.dao.Todo
import io.github.helvalius.model.repository.TodoRepository
import io.github.helvalius.model.resource.CreateTodoResource
import mu.KotlinLogging
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.persistence.PersistenceException
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
    fun createNewTodo(todoResource: CreateTodoResource): Todo? {  // TODO change return to transfer class
        val todo = Todo()
        todo.name = todoResource.name
        todo.description = todoResource.description ?: ""
        todo.tasks = todoResource.tasks.map {
            Task(name = it.name, description = it.description)
        }

        try {
            repository.persistAndFlush(todo)
            logger.info("Created new todo {$todo}")
        } catch( e : PersistenceException) {
            logger.error("Tried to create an already existing todo, use update instead")
            return null
        }
        return todo
    }

    fun getOne(id: Long): Todo =
        repository.findById(id) ?: throw Exception("Todo ID not found")   // TODO change return to transfer class

    fun getAll(): List<Todo> = repository.findAll().list()

    @Transactional
    fun delete(id: Long) = repository.deleteById(id)


}
