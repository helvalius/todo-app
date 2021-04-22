package io.github.helvalius.model.resource

data class UpdateTaskResource (
    val id: Long,
    val name: String,
    val description : String?
)


data class UpdateTodoResource(
    val id: Long,
    val name: String,
    val description : String?,
    val tasks : List<UpdateTaskResource> = listOf()
)

