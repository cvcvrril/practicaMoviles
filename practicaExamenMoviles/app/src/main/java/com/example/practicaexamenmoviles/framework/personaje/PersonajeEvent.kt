package com.example.practicaexamenmoviles.framework.personaje

sealed class PersonajeEvent {

    class DeletePersonaje(val idPersonaje: Int) : PersonajeEvent()
    class GetVideojuego(val idVideojuego: Int) : PersonajeEvent()
    class GetPersonajes() : PersonajeEvent()
    object ErrorVisto : PersonajeEvent()

}