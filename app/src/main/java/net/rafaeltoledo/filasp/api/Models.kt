package net.rafaeltoledo.filasp.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VaccinationPlace(
    @Json(name = "equipamento") val name: String,
    @Json(name = "endereco") val address: String,
    @Json(name = "distrito") val district: String,
    @Json(name = "crs") val region: String,
    @Json(name = "status_fila") val status: QueueStatus,
    @Json(name = "tipo_posto") val type: PlaceType,
    @Json(name = "data_hora") val updatedAt: String, // TODO format (2021-06-29 16:53:44.663)
)

enum class QueueStatus {
    @Json(name = "NÃO FUNCIONANDO") CLOSED,
    @Json(name = "SEM FILA") NO_QUEUE,
    @Json(name = "FILA GRANDE") LARGE_QUEUE,
    @Json(name = "FILA MÉDIA") MEDIUM_QUEUE,
    @Json(name = "FILA PEQUENA") SMALL_QUEUE,
}

enum class PlaceType {
    @Json(name = "DRIVE-THRU") DRIVE_THRU,
    @Json(name = "POSTO FIXO") PERMANENT,
    @Json(name = "POSTO VOLANTE") TEMPORARY,
    @Json(name = "MEGAPOSTO") MEGA,
}