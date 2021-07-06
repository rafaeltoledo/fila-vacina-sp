package net.rafaeltoledo.filasp.data

import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Json
import net.rafaeltoledo.filasp.api.QueueStatus

data class LocationData(
    @Json(name = "key") val key: String,
    @Json(name = "position") val position: String,
)

fun LocationData.latLng(): LatLng {
    val latlng = position.split(",")
    return LatLng(latlng[0].toDouble(), latlng[1].toDouble())
}

fun QueueStatus.mapIcon(): BitmapDescriptor {
    return when(this) {
        QueueStatus.CLOSED -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
        QueueStatus.NO_QUEUE -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
        QueueStatus.LARGE_QUEUE -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
        QueueStatus.MEDIUM_QUEUE -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
        QueueStatus.SMALL_QUEUE -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
        QueueStatus.AWAITING_DOSES -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
    }
}