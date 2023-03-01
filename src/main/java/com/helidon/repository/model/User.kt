package com.helidon.repository.model

import jakarta.persistence.Access
import jakarta.persistence.AccessType
import jakarta.persistence.Basic
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.Objects
import java.util.UUID

@Access(AccessType.FIELD)
@Entity(name = "User")
@Table(name = "users")
open class User(id: String, email: String) {
    constructor() : this(UUID.randomUUID().toString(), "")

    @Id
    @Column(name = "id", insertable = true, nullable = false, updatable = false)
    val id: String

    @Basic(optional = false)
    @Column(name = "email", insertable = true, nullable = false, updatable = true)
    val email: String

    init {
        this.id = Objects.requireNonNull(id)
        this.email = Objects.requireNonNull(email)
    }
}