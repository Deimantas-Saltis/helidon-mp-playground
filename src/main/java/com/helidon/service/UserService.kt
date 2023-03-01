package com.helidon.service

import com.helidon.repository.model.User
import com.helidon.service.model.UserCreated
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.eclipse.microprofile.opentracing.Traced

@Traced
@ApplicationScoped
open class UserService {

    companion object {
        const val GET_ALL_USERS_QUERY = "SELECT user FROM User user"
    }

    @Inject
    private lateinit var userEventsProducer: UserEventsProducer

    @PersistenceContext(unitName = "users")
    private lateinit var em: EntityManager


    open fun createUser(user: User) {
        val userCreated = UserCreated(user.id, user.email)
        userEventsProducer.publish(userCreated)
    }

    @Transactional
    open fun saveUser(user: User) {
        em.persist(user)
    }

    open fun getUsers(): List<User> {
        return em.createQuery(GET_ALL_USERS_QUERY, User::class.java).resultList
    }
}