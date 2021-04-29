package io.github.helvalius.model.dao

import javax.persistence.*

/**
 * Date: 21.04.21<br>
 * Author: Jan Nonnen
 * </p>
 */
@Entity
data class Todo(
    @Id
    @GeneratedValue
    var id: Long = 0,

    /*
    name of the Todo. This is required and unique  as this defines the Domain Entity identity.
    */
    @Column(nullable = false, unique = true)
    var name: String,

    @Column
    var description: String?,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "todo", fetch = FetchType.EAGER)
    var tasks: List<Task> = emptyList()

) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Todo

        return name.equals(other.name, ignoreCase = true)
    }

    override fun hashCode(): Int = name.hashCode()


    override fun toString(): String {
        return "$name: '$description', tasks: [$tasks]"
    }
}

