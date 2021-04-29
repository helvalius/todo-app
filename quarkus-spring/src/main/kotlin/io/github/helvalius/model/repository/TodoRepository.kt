package io.github.helvalius.model.repository;

import io.github.helvalius.model.dao.Todo;
import io.quarkus.hibernate.orm.panache.PanacheRepository
import org.springframework.data.repository.CrudRepository
import javax.enterprise.context.ApplicationScoped


/**
 * Date: 21.04.21<br>
 * Author: Jan Nonnen
 * </p>
 */
interface TodoRepository : CrudRepository<Todo, Long>

fun <T, ID> CrudRepository<T, ID>.findOne(id: ID): T? = findById(id).orElse(null)
