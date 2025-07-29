package com.illsang.common.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = false)
class StringListConverter : AttributeConverter<List<String>, String> {

    companion object {
        private const val DELIMITER = ","
    }

    override fun convertToDatabaseColumn(attribute: List<String>?): String? {
        return if (attribute.isNullOrEmpty()) {
            null
        } else {
            attribute.joinToString(DELIMITER)
        }
    }

    override fun convertToEntityAttribute(dbData: String?): List<String> {
        return if (dbData.isNullOrBlank()) {
            emptyList()
        } else {
            dbData.split(DELIMITER).map { it.trim() }.filter { it.isNotBlank() }
        }
    }
}
