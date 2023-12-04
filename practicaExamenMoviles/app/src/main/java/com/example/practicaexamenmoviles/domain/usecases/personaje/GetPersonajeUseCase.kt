package com.example.practicaexamenmoviles.domain.usecases.personaje

import com.example.practicaexamenmoviles.data.repositories.PersonajeRepository
import com.example.practicaexamenmoviles.domain.model.Personaje
import com.example.practicaexamenmoviles.utils.NetworkResult
import javax.inject.Inject

class GetPersonajeUseCase @Inject constructor(var repository: PersonajeRepository) {

    suspend operator fun invoke(idPersonaje: Int): NetworkResult<Personaje> = repository.getPersonaje(idPersonaje)
}