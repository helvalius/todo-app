package io.github.helvalius.model.dao

import javax.persistence.*

@Entity
data class Task(
    @Id
    @GeneratedValue
    var id: Long = 0,

    @Column(nullable = false)
    var name : String,

    @Column
    var description: String?
)
