package com.materiiapps.gloom.rest.utils

import com.apollographql.apollo3.api.Adapter
import com.apollographql.apollo3.api.CustomScalarAdapters
import com.apollographql.apollo3.api.StringAdapter
import com.apollographql.apollo3.api.json.JsonReader
import com.apollographql.apollo3.api.json.JsonWriter
import com.materiiapps.gloom.utils.TimeUtils.format
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

val DateAdapter = object : Adapter<Instant> {
    override fun fromJson(reader: JsonReader, customScalarAdapters: CustomScalarAdapters): Instant {
        val dateString = StringAdapter.fromJson(reader, customScalarAdapters)
        val (year, month, day) = dateString.split("-")
        return LocalDateTime(
            year.toInt(),
            month.toInt(),
            day.toInt(),
            0,
            0
        ).toInstant(TimeZone.currentSystemDefault())
    }

    override fun toJson(
        writer: JsonWriter,
        customScalarAdapters: CustomScalarAdapters,
        value: Instant
    ) {
        StringAdapter.toJson(writer, customScalarAdapters, value.format("YYYY-MM-dd"))
    }
}