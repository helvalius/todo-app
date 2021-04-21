package io.github.helvalius.model

import javax.persistence.*

/**
 * Date: 21.04.21<br>
 * Author: Jan Nonnen
 * </p>
 */
@Entity
data class Todo(
     @Column(nullable = false)
     val name : String,

     @Column
     val description : String?,

     @OneToMany
     val tasks : Set<Task> = setOf()
) {
    @Id
    @GeneratedValue
    var id : Long = 0
}
