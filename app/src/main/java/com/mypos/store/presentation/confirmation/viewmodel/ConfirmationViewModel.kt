package com.mypos.store.presentation.confirmation.viewmodel

import androidx.lifecycle.viewModelScope
import com.mypos.store.domain.articles.repository.ArticlesRepository
import com.mypos.store.presentation.base.viewmodel.BaseViewModel
import com.mypos.store.presentation.confirmation.model.ConfirmationModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository
) : BaseViewModel<ConfirmationUiState, ConfirmationUiEvent, ConfirmationEffect>(
    ConfirmationUiState()
) {
    override suspend fun handleEvent(event: ConfirmationUiEvent) {
        when (event) {
            ConfirmationUiEvent.ConfirmOrder -> {
                viewModelScope.launch {
                    articlesRepository.readAllArticlesSuspend().collect {
                        for (article in it) {
                            val copyOfArticle = article.copy()
                            copyOfArticle.removeFromCart()
                            articlesRepository.updateArticleSuspend(copyOfArticle)
                        }
                    }
                }

            }
        }

    }
}