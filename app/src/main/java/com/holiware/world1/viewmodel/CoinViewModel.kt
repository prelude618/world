package com.holiware.world1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.holiware.world1.model.Coin
import com.holiware.world1.model.CoinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CoinViewModel(
    val coinRepository: CoinRepository
): ViewModel() {

    private val _coinListState = MutableStateFlow<List<Coin>>(emptyList())
    val coinListState: StateFlow<List<Coin>> = _coinListState

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

        viewModelScope.launch {
            try {
                coinRepository.fetchCoins()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
