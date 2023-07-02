package com.mypos.store.presentation.cart.model

import com.mypos.store.domain.articles.model.ArticleEntity
import com.mypos.store.presentation.base.model.UiEvent
import com.mypos.store.presentation.base.model.UiSideEffect
import com.mypos.store.presentation.base.model.UiState

class CartModel {
    data class CartUiState(
        val isLoading: Boolean = false,
        val articles: List<ArticleEntity> = emptyList(),
        val total: Double = 0.0
    ) : UiState

    sealed class CartUiEvent : UiEvent {
        data class AddToCart(val shouldIncrease: Boolean, val article: ArticleEntity) : CartUiEvent()
    }

    class CartEffect : UiSideEffect
}