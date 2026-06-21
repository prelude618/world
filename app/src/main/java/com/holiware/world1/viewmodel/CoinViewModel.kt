package com.holiware.world1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.holiware.world1.model.Coin
import com.holiware.world1.model.CoinRepository
import com.holiware.world1.view.RequestState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CoinViewModel(
    val coinRepository: CoinRepository
): ViewModel() {

    private val _coinListState = MutableStateFlow<List<Coin>>(emptyList())
    val coinListState: StateFlow<List<Coin>> = _coinListState

    private val _requestState = MutableSharedFlow<RequestState>(replay = 1)
    val requestState: SharedFlow<RequestState> = _requestState.asSharedFlow()

    private var currentPage = 1

    init {
        viewModelScope.launch {
            try {
                coinRepository.getCoins().collectLatest { coins ->
                    _coinListState.value = coins
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        loadNextPage()
    }

    fun loadNextPage() {
        if (_requestState.replayCache.lastOrNull() == RequestState.Loading) return
        viewModelScope.launch {
            _requestState.emit(RequestState.Loading)
            try {
                coinRepository.fetchCoins(currentPage)
                currentPage++
                _requestState.emit(RequestState.Success)
            } catch (e: Exception) {
                e.printStackTrace()
                _requestState.emit(RequestState.Failure("Server is not responding"))
            }
        }
    }

    fun resetRequestState() {
        viewModelScope.launch {
            _requestState.emit(RequestState.Idle)
        }
    }
}
