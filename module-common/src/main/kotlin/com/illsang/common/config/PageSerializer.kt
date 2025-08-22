package com.illsang.common.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.boot.jackson.JsonComponent
import org.springframework.data.domain.Page

@JsonComponent
class PageSerializer : JsonSerializer<Page<*>>() {

    override fun serialize(page: Page<*>, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartObject()
        gen.writeObjectField("content", page.content)
        gen.writeNumberField("page", page.number)
        gen.writeNumberField("size", page.size)
        gen.writeNumberField("totalPages", page.totalPages)
        gen.writeNumberField("totalElements", page.totalElements)
        gen.writeBooleanField("isLast", page.isLast)
        gen.writeEndObject()
    }
}
