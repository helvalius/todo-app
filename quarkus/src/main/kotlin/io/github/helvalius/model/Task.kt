package io.github.helvalius.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Task(
    @Column(nullable = false)
    val name : String,
    @Column
    val description: String?)
{
    @Id
    @GeneratedValue
    val id: Long = 0
}
