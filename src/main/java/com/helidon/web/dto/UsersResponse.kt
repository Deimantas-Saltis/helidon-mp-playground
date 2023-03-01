package com.helidon.web.dto

import com.helidon.repository.model.User


data class UsersResponse(val users: List<UserResponse>) {
    companion object {
        fun from(users: List<User>) = UsersResponse(users.map { UserResponse.from(it) })
    }
}
