package io.github.helvalius.service

import io.github.helvalius.model.dao.Task
import io.github.helvalius.model.dao.Todo
import io.github.helvalius.model.repository.TodoRepository
import io.github.helvalius.model.repository.findOne
import io.github.helvalius.model.resource.CreateTodoDto
import io.github.helvalius.model.resource.UpdateTodoDto
import io.quarkus.hibernate.orm.panache.Panache
import mu.KotlinLogging
import org.springframework.stereotype.Service
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.persistence.PersistenceException
import javax.transaction.Transactional


/**
 * Date: 21.04.21<br>
 * Author: Jan Nonnen
 * </p>
 */
@Service
class TodoService(val repository: TodoRepository) {
    private val logger = KotlinLogging.logger {}

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
            repository.save(todo)
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

        return repository.save(todo)
    }

    fun getOne(id: Long): Todo =
        repository.findOne(id) ?: throw Exception("Todo ID not found")   // TODO change return to transfer class

    fun getAll(): List<Todo> = repository.findAll().toList()

    @Transactional
    fun delete(id: Long) = repository.deleteById(id)


}
