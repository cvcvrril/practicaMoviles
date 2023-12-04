package com.example.practicaexamenmoviles.framework.personaje

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicaexamenmoviles.domain.usecases.personaje.DeletePersonajeUseCase
import com.example.practicaexamenmoviles.domain.usecases.personaje.GetAllPersonajesUseCase
import com.example.practicaexamenmoviles.domain.usecases.videojuego.GetVideojuegoUseCase
import com.example.practicaexamenmoviles.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonajeViewModel @Inject constructor(
    private val getAllPersonajesUseCase: GetAllPersonajesUseCase,
    private val deletePersonajeUseCase: DeletePersonajeUseCase,
    private val getVideojuegoUseCase: GetVideojuegoUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData(PersonajeState())
    val uiState: LiveData<PersonajeState> get() = _uiState

    init {
        _uiState.value = PersonajeState(
            error = null,
        )
    }

    fun handleEvent(event: PersonajeEvent) {
        when (event) {
            is PersonajeEvent.DeletePersonaje -> {
                deletePersonaje(event.idPersonaje)
            }

            PersonajeEvent.ErrorVisto -> _uiState.value = _uiState.value?.copy(error = null)
            is PersonajeEvent.GetPersonajes -> {
                getPersonajes()
            }

            is PersonajeEvent.GetVideojuego -> {
                getVideojuego(event.idVideojuego)
                getPersonaje(event.idVideojuego)
            }
        }
    }

    private fun getPersonajes() {
        viewModelScope.launch {
            val videojuego = _uiState.value?.videojuego
            if (videojuego != null) {
                val result = getAllPersonajesUseCase(videojuego.id)
                when (result) {
                    is NetworkResult.Error -> {
                        _uiState.value = _uiState.value?.copy(error = result.message)
                        _uiState.value = _uiState.value?.copy(personajes = emptyList())
                    }

                    is NetworkResult.Success -> {
                        result.data?.let {
                            _uiState.value = _uiState.value?.copy(personajes = it)
                        }
                    }
                }
            }
        }
    }

    private fun getPersonaje(idVideojuego: Int) {
        viewModelScope.launch {
            val result = getAllPersonajesUseCase(idVideojuego)
            when (result) {
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(error = result.message)
                }

                is NetworkResult.Success -> {
                    result.data?.let {
                        _uiState.value = _uiState.value?.copy(personajes = it)
                    }
                }
            }
        }
    }

    private fun getVideojuego(idVideojuego: Int) {
        viewModelScope.launch {
            val result = getVideojuegoUseCase(idVideojuego)
            when (result) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(videojuego = result.data)
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(error = result.message)
                }
            }
        }
    }

    private fun deletePersonaje(idPersonaje: Int) {
        viewModelScope.launch {
            val result = deletePersonajeUseCase(idPersonaje)
            when (result) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(error = "Personaje eliminado")
                    getPersonajes()
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(error = result.message)
                }
            }
        }
    }

}