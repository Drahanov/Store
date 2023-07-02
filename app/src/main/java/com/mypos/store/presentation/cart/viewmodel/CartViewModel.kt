package com.mypos.store.presentation.cart.viewmodel

import androidx.lifecycle.viewModelScope
import com.mypos.store.domain.articles.repository.ArticlesRepository
import com.mypos.store.presentation.base.viewmodel.BaseViewModel
import com.mypos.store.presentation.cart.model.CartModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository,
) : BaseViewModel<CartUiState, CartUiEvent, CartEffect>(
    CartUiState()
) {
    init {
        viewModelScope.launch {
            articlesRepository.readAllArticlesSuspend().collect {
                setState { copy(articles = it) }

            }
        }
    }

    override suspend fun handleEvent(event: CartUiEvent) {
        when (event) {
            is CartUiEvent.AddToCart -> {
                val article = event.article.copy()
                article.cartAction(event.shouldIncrease)
                articlesRepository.updateArticleSuspend(article)
            }
        }
    }
}