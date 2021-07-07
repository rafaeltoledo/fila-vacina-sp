package net.rafaeltoledo.filasp.di

import android.content.Context
import android.content.res.AssetManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.rafaeltoledo.filasp.BuildConfig
import net.rafaeltoledo.filasp.api.DateTimeTimeAdapter
import net.rafaeltoledo.filasp.api.FilaSpService
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAssetManager(@ApplicationContext context: Context): AssetManager {
        return context.assets
    }

    @Provides
    @Reusable
    fun provideClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
                )
        )
        .build()

    @Provides
    @Reusable
    fun provideApiHost(): HttpUrl = "https://deolhonafila.prefeitura.sp.gov.br/".toHttpUrl()

    @Provides
    @Reusable
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(DateTimeTimeAdapter())
        .build()

    @Provides
    @Reusable
    fun provideRetrofit(client: OkHttpClient, host: HttpUrl, moshi: Moshi): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(host)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Reusable
    fun provideApi(retrofit: Retrofit): FilaSpService = retrofit.create()
}
