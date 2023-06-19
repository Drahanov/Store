package com.mypos.store.presentation.cart.viewmodel

import androidx.lifecycle.viewModelScope
import com.mypos.store.domain.articles.repository.ArticlesRepository
import com.mypos.store.domain.cart.repository.CartRepository
import com.mypos.store.presentation.base.viewmodel.BaseViewModel
import com.mypos.store.presentation.cart.model.CartModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    private val cartRepository: CartRepository
) : BaseViewModel<CartUiState, CartUiEvent, CartEffect>(
    CartUiState()
) {
    init {
        viewModelScope.launch {
            articlesRepository.readAllArticles().collect {
                setState { copy(articles = it) }

            }
        }

        viewModelScope.launch {
            cartRepository.cartState.collect {
                var totalPrice = 0.0
                for ((key, value) in it) {
                    for (article in uiState.value.articles) {
                        if (article.id == key) {
                            val price = article.price * value
                            totalPrice += price
                        }
                    }
                }
                setState { copy(cart = it, total = totalPrice) }
            }
        }
    }

    override suspend fun handleEvent(event: CartUiEvent) {
        when (event) {
            is CartUiEvent.AddToCart -> {
                cartRepository.addToCart(event.id, event.shouldIncrease)
            }

        }
    }
}