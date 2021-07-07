package net.rafaeltoledo.filasp.api

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

@JsonClass(generateAdapter = true)
data class VaccinationPlace(
    @Json(name = "equipamento") val name: String,
    @Json(name = "endereco") val address: String,
    @Json(name = "distrito") val district: String,
    @Json(name = "crs") val region: String,
    @Json(name = "status_fila") val status: QueueStatus,
    @Json(name = "tipo_posto") val type: PlaceType,
    @Json(name = "data_hora") val updatedAt: LocalDateTime,
    val position: LatLng = LatLng(.0, .0),
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        QueueStatus.values()[parcel.readInt()],
        PlaceType.values()[parcel.readInt()],
        LocalDateTime.parse(parcel.readString()!!, DateTimeTimeAdapter.format),
        parcel.readParcelable(LatLng::class.java.classLoader)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(district)
        parcel.writeString(region)
        parcel.writeInt(status.ordinal)
        parcel.writeInt(type.ordinal)
        parcel.writeString(DateTimeTimeAdapter.format.format(updatedAt))
        parcel.writeParcelable(position, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VaccinationPlace> {
        override fun createFromParcel(parcel: Parcel): VaccinationPlace {
            return VaccinationPlace(parcel)
        }

        override fun newArray(size: Int): Array<VaccinationPlace?> {
            return arrayOfNulls(size)
        }
    }
}

enum class QueueStatus {
    @Json(name = "NÃO FUNCIONANDO") CLOSED,
    @Json(name = "SEM FILA") NO_QUEUE,
    @Json(name = "FILA GRANDE") LARGE_QUEUE,
    @Json(name = "FILA MÉDIA") MEDIUM_QUEUE,
    @Json(name = "FILA PEQUENA") SMALL_QUEUE,
    @Json(name = "AGUARDANDO ABASTECIMENTO") AWAITING_DOSES,
}

enum class PlaceType {
    @Json(name = "DRIVE-THRU") DRIVE_THRU,
    @Json(name = "POSTO FIXO") PERMANENT,
    @Json(name = "POSTO VOLANTE") TEMPORARY,
    @Json(name = "MEGAPOSTO") MEGA,
}

class DateTimeTimeAdapter {
    @ToJson
    fun toJson(dateTime: LocalDateTime): String = format.format(dateTime)

    @FromJson
    fun fromJson(dateTime: String): LocalDateTime = LocalDateTime.parse(dateTime, format)

    companion object {
        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)
    }
}
