package com.helidon.web

import com.helidon.repository.model.User
import com.helidon.service.UserService
import com.helidon.web.dto.UserRequest
import com.helidon.web.dto.UsersResponse
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import java.util.UUID
import org.eclipse.microprofile.metrics.MetricUnits
import org.eclipse.microprofile.metrics.annotation.Counted
import org.eclipse.microprofile.metrics.annotation.Timed
import org.jboss.logging.Logger

@Path("/v1")
@RequestScoped
open class UsersApi {

    private val logger: Logger = Logger.getLogger(UsersApi::class.java)

    @Inject
    private lateinit var userService: UserService

    @POST
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Counted(name = "createUserCount", description = "How many users were created via API")
    @Timed(
        name = "createUserTimer",
        description = "A measure of how long it takes to create a user",
        unit = MetricUnits.MILLISECONDS
    )
    open fun createUser(userRequest: UserRequest) {
        val userId = UUID.randomUUID().toString()
        userService.createUser(User(userId, userRequest.email))

        logger.info("Received request for user creation `id`: $userId and `email`: ${userRequest.email}")
    }

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(name = "getUsersCount", description = "How many times user list was requested")
    @Timed(
        name = "getUsersTimer",
        description = "A measure of how long it takes to fetch users list",
        unit = MetricUnits.MILLISECONDS
    )
    open fun getUsers(): UsersResponse {
        val users = userService.getUsers()
        return UsersResponse.from(users)
    }
}