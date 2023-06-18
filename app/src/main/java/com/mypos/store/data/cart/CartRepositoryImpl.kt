package com.mypos.store.data.cart

import com.mypos.store.domain.cart.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartRepositoryImpl : CartRepository {
    private val _uiState = MutableStateFlow(HashMap<Int, Int>())
    val uiState: StateFlow<HashMap<Int, Int>> = _uiState.asStateFlow()


    override fun collectCart(): StateFlow<HashMap<Int, Int>> {
        return uiState
    }

    override suspend fun addToCart(id: Int) {
        _uiState.value = HashMap()
        _uiState.value.put(1, 2)
        _uiState.emit(uiState.value)
    }
}