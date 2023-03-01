package com.helidon.config.serialization

import com.helidon.service.model.UserCreated
import org.apache.kafka.common.serialization.Deserializer

class KafkaEventDeserializer : Deserializer<UserCreated> {
    private val objectMapper = ObjectMapperFactory.create()

    override fun deserialize(topic: String?, data: ByteArray?): UserCreated? {
        if (data == null) {
            return null
        }
        return objectMapper.readValue(String(data), UserCreated::class.java)
    }
}