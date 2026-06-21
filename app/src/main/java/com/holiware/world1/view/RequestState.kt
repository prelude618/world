package com.holiware.world1.view

sealed class RequestState {
    object Idle : RequestState()
    object Loading : RequestState()
    object Success : RequestState()
    data class Failure(val message: String) : RequestState()
}