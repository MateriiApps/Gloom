package dev.materii.gloom.api.dto.license

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class License(
    val key: String,
    val name: String,
    @SerialName("spdx_id")
    val spdxId: String,
)
