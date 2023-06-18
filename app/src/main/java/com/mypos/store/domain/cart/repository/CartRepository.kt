package com.mypos.store.domain.cart.repository

import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun collectCart(): Flow<HashMap<Int, Int>>
    suspend fun addToCart(id: Int)
}