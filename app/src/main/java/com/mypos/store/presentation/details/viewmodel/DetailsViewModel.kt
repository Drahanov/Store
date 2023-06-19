package com.mypos.store.presentation.details.viewmodel

import androidx.lifecycle.viewModelScope
import com.mypos.store.domain.articles.repository.ArticlesRepository
import com.mypos.store.domain.cart.repository.CartRepository
import com.mypos.store.presentation.base.viewmodel.BaseViewModel
import com.mypos.store.presentation.details.model.DetailsModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    private val cartRepository: CartRepository
) : BaseViewModel<DetailsUiState, DetailsUiEvent, DetailsEffect>(
    DetailsUiState()
) {
    override suspend fun handleEvent(event: DetailsUiEvent) {
        when (event) {
            is DetailsUiEvent.LoadDetails -> {
                viewModelScope.launch {
                    event.id?.let {
                        articlesRepository.getArticleById(it).collect {
                            setState { copy(articleEntity = it) }
                        }
                    }
                }

                viewModelScope.launch {
                    cartRepository.cartState.collect {
                        setState { copy(amountInCart = it) }
                    }

                }
            }

            is DetailsUiEvent.AddToCart -> {
                cartRepository.addToCart(event.id, event.shouldIncrease)
            }

        }
    }
}
