package com.example.practicaexamenmoviles.framework.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicaexamenmoviles.domain.model.Videojuego
import com.example.practicaexamenmoviles.domain.usecases.videojuego.DeleteVideojuegoUseCase
import com.example.practicaexamenmoviles.domain.usecases.videojuego.GetAllVideojuegosUseCase
import com.example.practicaexamenmoviles.utils.NetworkResult

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllVideojuegosUseCase: GetAllVideojuegosUseCase,
    private val deleteVideojuegoUseCase: DeleteVideojuegoUseCase
) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    private var selectedVideojuegos = mutableListOf<Videojuego>()

    private val _uiState = MutableLiveData(MainState())
    val uiState: LiveData<MainState> get() = _uiState

    init{
        _uiState.value = MainState(
            error = null,
            viedojuegosSelected = selectedVideojuegos.toList(),
            selectedMode = false
        )

    }


    fun handleEvent(event: MainEvent) {
        when (event) {
            MainEvent.GetVideojuegos -> {
                getVideojuegos()
            }

            MainEvent.ErrorVisto -> _uiState.value = _uiState.value?.copy(error = null)

            is MainEvent.DeleteVideojuego -> {
                deleteVideojuego(event.videojuego)
            }

            is MainEvent.SeleccionaVideojuegos -> seleccionaVideojuego(event.videojuego)
            is MainEvent.DeleteVideojuegosSeleccionados -> {
                deleteVideojuegos()
            }

            is MainEvent.GetVideojuegosFiltrados -> getVideojuegosFiltro(event.filtro)
            MainEvent.ResetSelectMode -> {
                resetSelectMode()
            }

            MainEvent.StartSelectedMode -> _uiState.value = _uiState.value?.copy(selectedMode = true)
        }
    }

    private fun getVideojuegos() {
        viewModelScope.launch {
            val result = getAllVideojuegosUseCase()
            when (result) {
                is NetworkResult.Error -> _error.value = result.message ?: "Ha habido un error"
                is NetworkResult.Success -> {
                    result.data?.let { videojuegos ->
                        _uiState.value = _uiState.value?.copy(videojuegos = videojuegos)
                    }
                }
            }
        }

    }

    private fun getVideojuegosFiltro(filtro: String) {
        viewModelScope.launch {
            val result = getAllVideojuegosUseCase()
            when(result){
                is NetworkResult.Error -> _error.value = result.message ?: ""
                is NetworkResult.Success -> {
                    result.data?.let { videojuegos ->
                        _uiState.value = _uiState.value?.copy(videojuegos = videojuegos.filter {
                            it.titulo.contains(
                                filtro,
                                ignoreCase = true
                            )
                        })
                    }
                }
            }
        }
    }

    private fun deleteVideojuegos() {
        viewModelScope.launch{
            val totalSelected = selectedVideojuegos.size
            var totalDeleted = 0
            for (videojuego in selectedVideojuegos){
                val result = deleteVideojuegoUseCase(videojuego.id)
                when(result){
                    is NetworkResult.Error -> _uiState.value = _uiState.value?.copy(error = result.message)
                    is NetworkResult.Success -> {
                        totalDeleted++
                    }
                }
            }
            _uiState.value =
                _uiState.value?.copy(error= "Total deleted: ${totalDeleted} of ${totalSelected}")
            selectedVideojuegos.clear()
            _uiState.value = _uiState.value?.copy(viedojuegosSelected = selectedVideojuegos.toList())
        }
    }

    private fun deleteVideojuego(videojuego: Videojuego) {
        viewModelScope.launch {
            val result = deleteVideojuegoUseCase(videojuego.id)
            when(result){
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(error = result.message)
                }
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(error = "Videojuego eliminado")
                }
            }
        }

    }

    private fun seleccionaVideojuego(videojuego: Videojuego) {
        if (isSelected(videojuego)){
            selectedVideojuegos.remove(videojuego)
            if (selectedVideojuegos.isEmpty()){
                _uiState.value = _uiState.value?.copy(selectedMode = false)
            }
        }else{
            selectedVideojuegos.add(videojuego)
        }
        _uiState.value = _uiState.value?.copy(viedojuegosSelected = selectedVideojuegos)
    }


    private fun resetSelectMode() {
        selectedVideojuegos.clear()
        _uiState.value =
            _uiState.value?.copy(selectedMode = false, viedojuegosSelected = selectedVideojuegos)
    }

    private fun isSelected(videojuego: Videojuego): Boolean{
        return selectedVideojuegos.contains(videojuego)
    }
}