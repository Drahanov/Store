package com.mypos.store.presentation.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.mypos.store.domain.articles.repository.ArticlesRepository
import com.mypos.store.domain.cart.repository.CartRepository
import com.mypos.store.presentation.base.viewmodel.BaseViewModel
import com.mypos.store.presentation.home.model.HomeModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    private val cartRepository: CartRepository
) : BaseViewModel<HomeUiState, HomeUiEvent, HomeUiEffect>(
    HomeUiState()
) {
    override suspend fun handleEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.LoadArticles -> {
                setState { copy(isLoading = true) }
                viewModelScope.launch {
                    articlesRepository.readAllArticlesFlow().collect() {
                        setState { copy(isLoading = false, articles = it) }
                    }
                }
                viewModelScope.launch {
                    cartRepository.cartState.collectLatest {
                        setState { copy(cart = it) }
                    }
                }
            }

            is HomeUiEvent.AddToCart -> {
                cartRepository.addToCart(event.id, event.shouldIncrease)
            }
        }
    }


}