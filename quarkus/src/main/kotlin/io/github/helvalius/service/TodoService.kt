package io.github.helvalius.service

import io.github.helvalius.model.dao.Task
import io.github.helvalius.model.dao.Todo
import io.github.helvalius.model.repository.TodoRepository
import io.github.helvalius.model.resource.CreateTodoDto
import io.github.helvalius.model.resource.UpdateTodoDto
import io.quarkus.hibernate.orm.panache.Panache
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
    fun createNewTodo(todoDto: CreateTodoDto): Todo? {  // TODO change return to transfer class
        val todo =
            Todo(
                name = todoDto.name,
                description = todoDto.description,
                tasks = todoDto.tasks.map {
                    Task(name = it.name, description = it.description)
                })

        try {
            repository.persistAndFlush(todo)
            logger.info("Created new todo {$todo}")
        } catch (e: PersistenceException) {
            logger.error("Tried to create an already existing todo, use update instead")
            return null
        }
        return todo
    }

    @Transactional
    fun update(id: Long, updatedTodo: UpdateTodoDto): Todo {
        val todo = Todo(
            id = id,
            name = updatedTodo.name,
            description = updatedTodo.description,
            tasks = updatedTodo.tasks.map {
                Task(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                )
            })

        return Panache.getEntityManager().merge(todo)
    }

    fun getOne(id: Long): Todo =
        repository.findById(id) ?: throw Exception("Todo ID not found")   // TODO change return to transfer class

    fun getAll(): List<Todo> = repository.findAll().list()

    @Transactional
    fun delete(id: Long) = repository.deleteById(id)


}
