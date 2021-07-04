package net.rafaeltoledo.filasp

import android.app.Application
import com.mapbox.mapboxsdk.Mapbox
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))
    }
}
