package net.rafaeltoledo.filasp.data

import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Json

data class LocationData(
    @Json(name = "key") val key: String,
    @Json(name = "position") val position: String,
)

fun LocationData.latLng(): LatLng {
    val latlng = position.split(",")
    return LatLng(latlng[0].toDouble(), latlng[1].toDouble())
}