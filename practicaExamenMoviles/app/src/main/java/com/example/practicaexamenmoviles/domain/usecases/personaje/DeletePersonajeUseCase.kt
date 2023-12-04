package com.example.practicaexamenmoviles.domain.usecases.personaje

import com.example.practicaexamenmoviles.data.repositories.PersonajeRepository
import javax.inject.Inject

class DeletePersonajeUseCase @Inject constructor(var repository: PersonajeRepository) {

    suspend operator fun invoke(idPersonaje: Int) = repository.deletePersonaje(idPersonaje)
}