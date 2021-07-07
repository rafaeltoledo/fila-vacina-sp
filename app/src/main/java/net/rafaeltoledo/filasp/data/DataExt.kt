package net.rafaeltoledo.filasp.data

import android.content.res.AssetManager
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import okio.buffer
import okio.source

fun AssetManager.readAsset(fileName: String = "latlng.json"): String {
    return open(fileName).reader().readText()
}

fun Moshi.parseData(rawData: String): List<LocationData> {
    val reader = JsonReader.of(rawData.byteInputStream().source().buffer())
    val list = mutableListOf<LocationData>()

    val names = JsonReader.Options.of("key", "position")

    reader.beginArray()
    while (reader.hasNext()) {
        reader.beginObject()
        var key = ""
        var position = ""
        while (reader.hasNext()) {
            when (reader.selectName(names)) {
                0 -> key = reader.nextString()
                1 -> position = reader.nextString()
                else -> {
                    reader.skipName()
                    reader.skipValue()
                }
            }
        }
        reader.endObject()

        list.add(LocationData(key, position))
    }
    reader.endArray()

    return list.toList()
}
