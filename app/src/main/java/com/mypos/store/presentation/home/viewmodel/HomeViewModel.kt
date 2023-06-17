package com.mypos.store.presentation.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.mypos.store.domain.articles.repository.ArticlesRepository
import com.mypos.store.presentation.base.viewmodel.BaseViewModel
import com.mypos.store.presentation.home.model.HomeModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ArticlesRepository
) : BaseViewModel<HomeUiState, HomeUiEvent, HomeUiEffect>(
    HomeUiState()
) {
    override suspend fun handleEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.LoadArticles -> {
                setState { copy(isLoading = true) }
                viewModelScope.launch {
                    repository.readAllArticles().collect() {
                        setState { copy(isLoading = false, articles = it) }
                    }
                }
            }
        }
    }
}