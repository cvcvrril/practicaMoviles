package com.example.practicaexamenmoviles.data.model

import com.example.practicaexamenmoviles.domain.model.Videojuego
import com.google.gson.annotations.SerializedName

data class VideojuegoResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("titulo")
    val titulo: String,
    @SerializedName("year")
    val year: Int
)
fun VideojuegoResponse.toVideojuego() : Videojuego = Videojuego(id, titulo, year)