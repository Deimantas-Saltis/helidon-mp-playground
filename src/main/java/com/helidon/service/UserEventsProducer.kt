package com.helidon.service

import com.helidon.service.model.UserCreated
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.jboss.logging.Logger

@ApplicationScoped
open class UserEventsProducer {

    @Inject
    @Channel("users-out")
    private lateinit var userEventsEmitter: Emitter<UserCreated>

    private val logger: Logger = Logger.getLogger(UserEventsProducer::class.java)

    open fun publish(event: UserCreated) {
        logger.info("Publishing event: $event")
        userEventsEmitter.send(event)
    }
}