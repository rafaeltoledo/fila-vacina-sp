package net.rafaeltoledo.filasp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.conscrypt.OpenSSLProvider
import java.security.Security

@HiltAndroidApp
class MainApp : Application() {

    companion object {
        init {
            Security.insertProviderAt(OpenSSLProvider(), 1)
        }
    }
}
