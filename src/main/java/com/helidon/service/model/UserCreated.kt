package com.helidon.service.model

import com.helidon.repository.model.User

data class UserCreated(val id: String, val email: String) {
    fun toUser() = User(id, email)
}