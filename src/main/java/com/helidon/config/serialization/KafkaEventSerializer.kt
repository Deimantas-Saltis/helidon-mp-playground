package com.helidon.config.serialization

import com.helidon.service.model.UserCreated
import org.apache.kafka.common.serialization.Serializer

class KafkaEventSerializer : Serializer<UserCreated> {
    private val objectMapper = ObjectMapperFactory.create()

    override fun serialize(topic: String, data: UserCreated): ByteArray = objectMapper.writeValueAsBytes(data)
}