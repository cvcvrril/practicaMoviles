package com.example.practicaexamenmoviles.data.sources.remote

import com.example.practicaexamenmoviles.data.model.PersonajeResponse
import com.example.practicaexamenmoviles.data.model.VideojuegoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface PersonajeService {

    @GET("https://my-json-server.typicode.com/cvcvrril/REST-Videojuegos/personaje/")
    suspend fun getAllPersonajes(): Response<List<PersonajeResponse>>
    @GET("https://my-json-server.typicode.com/cvcvrril/REST-Videojuegos/personaje/{id}")
    suspend fun getPersonaje(@Path("id") personajeId: Int): Response<PersonajeResponse>
    @DELETE("https://my-json-server.typicode.com/cvcvrril/REST-Videojuegos/personaje/{id}")
    suspend fun deletePersonaje(@Path("id") personajeId: Int): Response<Unit>
    @POST("https://my-json-server.typicode.com/cvcvrril/REST-Videojuegos/personaje/")
    @Headers("Content-Type: application/json")
    suspend fun addPersonaje(@Body personajeResponse: PersonajeResponse): Response<Unit>
}