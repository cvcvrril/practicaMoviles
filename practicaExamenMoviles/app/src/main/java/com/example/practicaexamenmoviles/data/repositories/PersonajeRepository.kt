package com.example.practicaexamenmoviles.data.repositories

import com.example.practicaexamenmoviles.data.sources.remote.RemoteDataSource
import com.example.practicaexamenmoviles.domain.model.Personaje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PersonajeRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getPersonajes(id: Int) =
        withContext(Dispatchers.IO) {
            remoteDataSource.getPersonajes(id)
        }

    suspend fun getPersonaje(idPersonaje: Int) =
        withContext(Dispatchers.IO) {
            remoteDataSource.getPersonaje(idPersonaje)
        }

    suspend fun deletePersonaje(idPersonaje: Int) =
        withContext(Dispatchers.IO) {
            remoteDataSource.deletePersonaje(idPersonaje)
        }

    suspend fun addPersonaje(personaje: Personaje) =
        withContext(Dispatchers.IO) {
            remoteDataSource.addPersonaje(personaje)
        }

}