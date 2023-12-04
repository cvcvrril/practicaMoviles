package com.example.practicaexamenmoviles.data.model

import com.example.practicaexamenmoviles.domain.model.Personaje
import com.google.gson.annotations.SerializedName

data class PersonajeResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("idVideojuego")
    val idVideojuego: Int
)
fun PersonajeResponse.toPersonaje(): Personaje = Personaje(id, name, idVideojuego)