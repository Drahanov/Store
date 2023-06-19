package com.mypos.store.domain.cart.repository

import kotlinx.coroutines.flow.StateFlow

interface CartRepository {
    val cartState: StateFlow<HashMap<Int, Int>>
    suspend fun addToCart(id: Int, increase: Boolean)
}