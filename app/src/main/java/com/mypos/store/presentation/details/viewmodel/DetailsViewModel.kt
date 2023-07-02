package com.mypos.store.presentation.details.viewmodel

import androidx.lifecycle.viewModelScope
import com.mypos.store.domain.articles.repository.ArticlesRepository
import com.mypos.store.presentation.base.viewmodel.BaseViewModel
import com.mypos.store.presentation.details.model.DetailsModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository,
) : BaseViewModel<DetailsUiState, DetailsUiEvent, DetailsEffect>(
    DetailsUiState()
) {
    override suspend fun handleEvent(event: DetailsUiEvent) {
        when (event) {
            is DetailsUiEvent.LoadDetails -> {

                setState { copy(isLoading = true) }
                viewModelScope.launch {
                    event.id?.let {
                        articlesRepository.getArticleByIdSuspend(it).collect {
                            setState { copy(articleEntity = it) }
                        }

                        setState { copy(isLoading = false) }
                    }
                }
            }

            is DetailsUiEvent.AddToCart -> {
                val article = uiState.value.articleEntity?.copy()
                article?.cartAction(event.shouldIncrease)
                article?.let { articlesRepository.updateArticleSuspend(it) }
            }

            is DetailsUiEvent.Delete -> {
                articlesRepository.removeArticleSuspend(event.article)
                setEffect(DetailsEffect.NotifyDeleteItem(event.article))
            }
        }
    }
}
