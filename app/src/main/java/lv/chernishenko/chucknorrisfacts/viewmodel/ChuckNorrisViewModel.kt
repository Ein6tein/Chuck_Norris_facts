package lv.chernishenko.chucknorrisfacts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import lv.chernishenko.chucknorrisfacts.model.ChuckNorrisFact
import lv.chernishenko.chucknorrisfacts.repository.ChuckNorrisRepository
import lv.chernishenko.chucknorrisfacts.usecase.ChuckNorrisLocalFactsUseCase
import lv.chernishenko.chucknorrisfacts.usecase.ChuckNorrisSaveFactUseCase
import javax.inject.Inject

@HiltViewModel
class ChuckNorrisViewModel @Inject constructor(
    private val repository: ChuckNorrisRepository,
    private val localFactsUseCase: ChuckNorrisLocalFactsUseCase,
    private val saveFactUseCase: ChuckNorrisSaveFactUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainScreenState>(MainScreenState.Initial)
    val uiState = _uiState.asStateFlow()

    val pager = localFactsUseCase().cachedIn(viewModelScope)

    fun getRandomFact() {
        _uiState.value = MainScreenState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            repository.randomFact().onSuccess { fact ->
                viewModelScope.launch {
                    saveFactUseCase(fact)
                    localFactsUseCase()
                }
                _uiState.value = MainScreenState.Success(fact)
            }.onFailure { error ->
                _uiState.value = MainScreenState.Error(error.message ?: "Unknown error")
            }
        }
    }
}

open class MainScreenState {
    data object Initial : MainScreenState()
    data object Loading : MainScreenState()
    data class Success(val fact: ChuckNorrisFact?) : MainScreenState()
    data class Error(val message: String) : MainScreenState()
}