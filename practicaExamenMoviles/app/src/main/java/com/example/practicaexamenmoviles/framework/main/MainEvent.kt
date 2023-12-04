package com.example.practicaexamenmoviles.framework.main

import com.example.practicaexamenmoviles.domain.model.Videojuego

sealed class MainEvent {

    class DeleteVideojuegosSeleccionados() : MainEvent()
    class DeleteVideojuego(val videojuego: Videojuego) : MainEvent()
    class SeleccionaVideojuegos(val videojuego: Videojuego) : MainEvent()

    class GetVideojuegosFiltrados(val filtro: String) : MainEvent()
    object GetVideojuegos : MainEvent()
    object ErrorVisto : MainEvent()

    object StartSelectedMode : MainEvent()
    object ResetSelectMode : MainEvent()



}