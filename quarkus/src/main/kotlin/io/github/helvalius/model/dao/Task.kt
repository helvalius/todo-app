package io.github.helvalius.model.dao

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Task(
    @Column(nullable = false)
    var name : String,

    @Column
    var description: String?)
{
    @Id
    @GeneratedValue
    var id: Long = 0
}
