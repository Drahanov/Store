package com.mypos.store.presentation.cart.model

import com.mypos.store.domain.articles.model.ArticleEntity
import com.mypos.store.presentation.base.model.UiEvent
import com.mypos.store.presentation.base.model.UiSideEffect
import com.mypos.store.presentation.base.model.UiState
import com.mypos.store.presentation.home.model.HomeModel

class CartModel {
    data class CartUiState(
        val isLoading: Boolean = false,
        val articles: List<ArticleEntity> = emptyList(),
        val cart: HashMap<Int, Int> = HashMap(),
        val total: Double = 0.0
    ) : UiState

    sealed class CartUiEvent : UiEvent {
        data class AddToCart(val shouldIncrease: Boolean, val id: Int) : CartUiEvent()

    }

    class CartEffect : UiSideEffect
}