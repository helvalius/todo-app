package io.github.helvalius.model.resource

import io.github.helvalius.model.dao.Todo
import javax.validation.constraints.NotEmpty

data class UpdateTaskDto (
    val id: Long,
    @field:NotEmpty
    val name: String,
    val description : String?
)


data class UpdateTodoDto(
    val id: Long,
    @field:NotEmpty
    val name: String,
    val description : String?,
    val tasks : List<UpdateTaskDto> = listOf()
)

fun Todo.toTodoDto() = UpdateTodoDto(
    id = id,
    name = name,
    description = description,
    tasks = tasks.map{ UpdateTaskDto(id=it.id, name=it.name, description=it.description)}
)

