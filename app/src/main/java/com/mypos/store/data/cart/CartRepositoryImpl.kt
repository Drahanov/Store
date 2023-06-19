package com.mypos.store.data.cart

import com.mypos.store.domain.cart.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartRepositoryImpl : CartRepository {

    private val _cartState = MutableStateFlow(HashMap<Int, Int>())
    override val cartState: StateFlow<HashMap<Int, Int>> = _cartState

    override fun addToCart(id: Int, increase: Boolean) {
        val tempCartState = HashMap(cartState.value)
        if (tempCartState.containsKey(id)) {
            var amount = tempCartState[id]
            amount?.let {
                if (increase) {
                    tempCartState.put(id, amount.plus(1))
                } else {
                    if (cartState.value[id] != 0) {
                        tempCartState.put(id, amount.minus(1))
                    } else {
                        // do nothing when cart is empty for this item
                    }
                }
            }
        } else {
            tempCartState[id] = 1
        }

        _cartState.value = tempCartState
    }

    override fun clearCart() {
        _cartState.value = HashMap()
    }
}