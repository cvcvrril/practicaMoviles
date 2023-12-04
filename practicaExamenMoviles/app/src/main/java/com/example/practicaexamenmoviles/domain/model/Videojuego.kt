package com.example.practicaexamenmoviles.domain.model

data class Videojuego (
    val id: Int,
    val titulo: String,
    val year: Int,
    var isSelected: Boolean = false
)