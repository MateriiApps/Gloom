package dev.materii.gloom.api.util

import com.apollographql.apollo.api.Adapter
import com.apollographql.apollo.api.CustomScalarAdapters
import com.apollographql.apollo.api.StringAdapter
import com.apollographql.apollo.api.json.JsonReader
import com.apollographql.apollo.api.json.JsonWriter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

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
        val ldt = value.toLocalDateTime(TimeZone.currentSystemDefault())
        StringAdapter.toJson(
            writer,
            customScalarAdapters,
            "${ldt.year}-${ldt.monthNumber}-${ldt.dayOfMonth}"
        )
    }
}