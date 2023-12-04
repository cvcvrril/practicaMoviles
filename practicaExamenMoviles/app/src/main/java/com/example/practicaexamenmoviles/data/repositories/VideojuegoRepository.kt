package com.example.practicaexamenmoviles.data.repositories

import android.util.Log
import com.example.practicaexamenmoviles.data.sources.remote.RemoteDataSource
import com.example.practicaexamenmoviles.domain.model.Videojuego
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class VideojuegoRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getVideojuegos() =
        withContext(Dispatchers.IO) {
            remoteDataSource.getVideojuegos()
        }

    suspend fun getVideojuego(idVideojuego: Int) =
        withContext(Dispatchers.IO){
            remoteDataSource.getVideojuego(idVideojuego)
        }

    suspend fun deleteVideojuego(idVideojuego: Int) =
        withContext(Dispatchers.IO){
            remoteDataSource.deleteVideojuego(idVideojuego)
        }

}
