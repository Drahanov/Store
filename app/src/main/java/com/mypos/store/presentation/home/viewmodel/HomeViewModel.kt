package com.mypos.store.presentation.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.mypos.store.domain.articles.repository.ArticlesRepository
import com.mypos.store.domain.cart.repository.CartRepository
import com.mypos.store.presentation.base.viewmodel.BaseViewModel
import com.mypos.store.presentation.home.model.HomeModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
                    articlesRepository.readAllArticles().collect() {
                        setState { copy(isLoading = false, articles = it) }
                    }
                }
                viewModelScope.launch {
                    cartRepository.collectCart().collect() {
                        setState { copy(cart = it) }
                    }

                }

                viewModelScope.launch {
                    delay(4000)
                    cartRepository.addToCart(12)

                }
            }
        }
    }


}