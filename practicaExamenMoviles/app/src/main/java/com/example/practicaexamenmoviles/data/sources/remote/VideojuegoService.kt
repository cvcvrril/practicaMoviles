package com.example.practicaexamenmoviles.data.sources.remote

import com.example.practicaexamenmoviles.data.model.VideojuegoResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface VideojuegoService {
    @GET("https://my-json-server.typicode.com/cvcvrril/REST-Videojuegos/videojuego/")
    suspend fun getAllVideojuegos(): Response<List<VideojuegoResponse>>
    @GET("https://my-json-server.typicode.com/cvcvrril/REST-Videojuegos/videojuego/{id}")
    suspend fun getVideojuego(@Path("id") videojuegoId: Int): Response<VideojuegoResponse>
    @DELETE("https://my-json-server.typicode.com/cvcvrril/REST-Videojuegos/videojuego/{id}")
    suspend fun deleteVideojuego(@Path("id") videojuegoId: Int): Response<Unit>

}