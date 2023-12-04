package com.example.practicaexamenmoviles.data.sources.remote.di;

import com.example.practicaexamenmoviles.data.sources.remote.PersonajeService
import com.example.practicaexamenmoviles.data.sources.remote.VideojuegoService
import com.google.gson.GsonBuilder
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
                .Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
            GsonConverterFactory.create(GsonBuilder().setLenient().create())
    @Singleton
    @Provides
    fun provideRetrofit(
            okHttpClient:OkHttpClient,
            gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/cvcvrril/REST-Videojuegos/")
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .build()
    }

    @Singleton
    @Provides
    fun provideCurrencyService(retrofit: Retrofit): VideojuegoService =
            retrofit.create(VideojuegoService::class.java)


    @Singleton
    @Provides
    fun providePeliculaService(retrofit: Retrofit): PersonajeService =
        retrofit.create(PersonajeService::class.java)

}
