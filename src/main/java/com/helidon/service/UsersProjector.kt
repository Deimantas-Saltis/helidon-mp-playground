package com.helidon.service

import com.helidon.service.model.UserCreated
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.opentracing.Traced
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.jboss.logging.Logger

@Traced
@ApplicationScoped
open class UsersProjector {

    @Inject
    private lateinit var userService: UserService

    private val logger: Logger = Logger.getLogger(UsersProjector::class.java)

    @Incoming("users")
    open fun on(event: UserCreated) {
        logger.info("Consuming event: $event")
        userService.saveUser(event.toUser())
    }
}