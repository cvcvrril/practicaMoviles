package com.example.practicaexamenmoviles.domain.usecases.videojuego

import com.example.practicaexamenmoviles.data.repositories.VideojuegoRepository
import com.example.practicaexamenmoviles.domain.model.Videojuego
import com.example.practicaexamenmoviles.utils.NetworkResult
import javax.inject.Inject

class GetAllVideojuegosUseCase @Inject constructor(var repository: VideojuegoRepository) {
    suspend operator fun invoke(): NetworkResult<List<Videojuego>> = repository.getVideojuegos()

}