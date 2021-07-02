package net.rafaeltoledo.filasp.data

import com.squareup.moshi.Json

data class LocationData(
    @Json(name = "key") val key: String,
    @Json(name = "position") val position: String,
)