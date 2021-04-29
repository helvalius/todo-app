package io.github.helvalius.model.resource

import javax.validation.constraints.NotEmpty

data class CreateTaskDto (
    @field:NotEmpty
    val name: String,
    val description: String?
)


data class CreateTodoDto(
    @field:NotEmpty
    val name: String,
    val description: String?,
    val tasks: List<CreateTaskDto> = listOf()
)

