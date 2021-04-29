package io.github.helvalius.service

import io.github.helvalius.model.dao.Task
import io.github.helvalius.model.dao.Todo
import io.github.helvalius.model.repository.TodoRepository
import io.github.helvalius.model.resource.CreateTodoDto
import io.github.helvalius.model.resource.UpdateTodoDto
import io.github.helvalius.model.resource.toTodoDto
import io.quarkus.hibernate.orm.panache.Panache
import mu.KotlinLogging
import org.hibernate.sql.Update
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
    fun createNewTodo(todoDto: CreateTodoDto): UpdateTodoDto? {  // TODO change return to transfer class
        val todo =
            Todo(
                name = todoDto.name,
                description = todoDto.description,
            )

        todo.tasks = todoDto.tasks.map {
            Task(name = it.name, description = it.description, todo = todo)
        }

        try {
            repository.persistAndFlush(todo)
            logger.info("Created new todo {$todo}")
        } catch (e: PersistenceException) {
            logger.error("Tried to create an already existing todo, use update instead")
            return null
        }
        return todo.toTodoDto()
    }

    @Transactional
    fun update(id: Long, updatedTodo: UpdateTodoDto): UpdateTodoDto {
        val todo = Todo(
            id = id,
            name = updatedTodo.name,
            description = updatedTodo.description
        )

        todo.tasks = updatedTodo.tasks.map {
            Task(
                id = it.id,
                name = it.name,
                description = it.description,
                todo = todo
            )
        }

        return Panache.getEntityManager().merge(todo).toTodoDto()
    }

    fun getOne(id: Long): UpdateTodoDto =
        (repository.findById(id) ?: throw Exception("Todo ID not found")).toTodoDto()   // TODO change return to transfer class

    fun getAll(): List<UpdateTodoDto> = repository.findAll().list<Todo>().map{ it.toTodoDto()}

    @Transactional
    fun delete(id: Long) = repository.deleteById(id)


}
