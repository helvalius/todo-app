package io.github.helvalius.model.resource

data class CreateTaskResource (
    val name: String,
    val description : String?
)


data class CreateTodoResource(
    val name: String,
    val description : String?,
    val tasks : List<CreateTaskResource> = listOf()
)

