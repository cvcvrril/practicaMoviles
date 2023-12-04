package com.example.practicaexamenmoviles.framework.personaje

import com.example.practicaexamenmoviles.domain.model.Personaje
import com.example.practicaexamenmoviles.domain.model.Videojuego

data class PersonajeState(
    val personajes: List<Personaje> = emptyList(),
    val videojuego: Videojuego? = null,
    val error: String? = null)