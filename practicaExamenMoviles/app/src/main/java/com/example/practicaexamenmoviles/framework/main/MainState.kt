package com.example.practicaexamenmoviles.framework.main

import com.example.practicaexamenmoviles.domain.model.Videojuego

data class MainState(
    val videojuegos: List<Videojuego> = emptyList(),
    val viedojuegosSelected: List<Videojuego> = emptyList(),
    val selectedMode: Boolean = false,
    val error: String? = null
)