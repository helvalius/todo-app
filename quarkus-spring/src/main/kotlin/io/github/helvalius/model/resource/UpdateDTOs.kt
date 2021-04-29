package io.github.helvalius.model.resource

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

