package net.rafaeltoledo.filasp.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.rafaeltoledo.filasp.BuildConfig
import net.rafaeltoledo.filasp.api.FilaSpService
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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