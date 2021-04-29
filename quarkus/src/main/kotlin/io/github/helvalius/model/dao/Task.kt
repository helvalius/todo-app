package io.github.helvalius.model.dao

import javax.persistence.*

@Entity
data class Task(
    @Id
    @GeneratedValue
    var id: Long = 0,

    @Column(nullable = false)
    var name: String,

    @Column
    var description: String?,

    @ManyToOne
    @JoinColumn(name = "todo_id")
    var todo: Todo? = null


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        return name.equals(other.name, ignoreCase = true) && todo!!.name == other.todo!!.name
    }

    override fun hashCode(): Int = name.hashCode() + todo!!.name.hashCode()


    override fun toString(): String {
        return "$name: '$description'"
    }
}
