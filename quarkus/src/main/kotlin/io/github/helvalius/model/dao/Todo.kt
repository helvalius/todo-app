package io.github.helvalius.model.dao

import io.quarkus.hibernate.orm.panache.PanacheEntity
import javax.persistence.*

/**
 * Date: 21.04.21<br>
 * Author: Jan Nonnen
 * </p>
 */
@Entity
data class Todo  (
    @Column(nullable = false)
     var name : String,

    @Column
    var description : String,

    @OneToMany
    var tasks : List<Task>
)  {
    @Id
    @GeneratedValue
    var id : Long = 0

}
