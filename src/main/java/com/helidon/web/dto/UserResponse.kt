package com.helidon.web.dto

import com.helidon.repository.model.User

data class UserResponse(val id: String, val email: String) {
    companion object {
        fun from(user: User) = UserResponse(user.id, user.email)
    }
}
