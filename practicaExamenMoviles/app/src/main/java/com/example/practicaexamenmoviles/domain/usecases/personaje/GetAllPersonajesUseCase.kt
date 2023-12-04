package com.example.practicaexamenmoviles.domain.usecases.personaje

import com.example.practicaexamenmoviles.data.repositories.PersonajeRepository
import com.example.practicaexamenmoviles.domain.model.Personaje
import com.example.practicaexamenmoviles.utils.NetworkResult
import javax.inject.Inject

class GetAllPersonajesUseCase @Inject constructor(var repository: PersonajeRepository) {
    suspend operator fun invoke(id: Int): NetworkResult<List<Personaje>> = repository.getPersonajes(id)
}