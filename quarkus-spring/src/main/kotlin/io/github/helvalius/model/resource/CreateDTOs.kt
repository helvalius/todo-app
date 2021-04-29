package io.github.helvalius.model.resource

import javax.validation.constraints.NotEmpty
import javax.validation.groups.Default

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


// @ConvertGroup(to = ValidationGroups.Update.class)
// @ConvertGroup(to = ValidationGroups.Create.class)
interface ValidationGroups {
    interface Update : Default
    interface Create : Default
}
