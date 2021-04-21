package io.github.helvalius.model.dao

import io.quarkus.hibernate.orm.panache.PanacheEntity
import javax.persistence.*

/**
 * Date: 21.04.21<br>
 * Author: Jan Nonnen
 * </p>
 */
@Entity
class Todo   {
    @Id
    @GeneratedValue
    var id : Long = 0

    /*
     name of the Todo. This is required and unique  as this defines the Domain Entity identity.
     */
    @Column(nullable = false, unique = true)
    lateinit var name : String

    @Column
    lateinit var description : String

    @OneToMany
    var tasks : List<Task> = listOf()

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
