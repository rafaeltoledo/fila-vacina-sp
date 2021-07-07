package net.rafaeltoledo.filasp

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import org.conscrypt.OpenSSLProvider
import java.security.Security

@HiltAndroidApp
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        FirebaseCrashlytics.getInstance().setCustomKey("GIT_SHA", BuildConfig.GIT_SHA)
    }

    companion object {
        init {
            Security.insertProviderAt(OpenSSLProvider(), 1)
        }
    }
}
