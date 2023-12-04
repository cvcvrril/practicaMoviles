package com.example.practicaexamenmoviles.domain.usecases.personaje

import com.example.practicaexamenmoviles.data.repositories.PersonajeRepository
import com.example.practicaexamenmoviles.domain.model.Personaje
import javax.inject.Inject

class AddPersonajeUseCase @Inject constructor(var repository: PersonajeRepository) {

    suspend operator fun invoke(personaje: Personaje) = repository.addPersonaje(personaje)

}