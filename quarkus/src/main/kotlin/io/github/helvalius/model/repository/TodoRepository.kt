package io.github.helvalius.model.repository;

import io.github.helvalius.model.dao.Todo;
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped


/**
 * Date: 21.04.21<br>
 * Author: Jan Nonnen
 * </p>
 */
@ApplicationScoped
class TodoRepository : PanacheRepository<Todo> {
}
