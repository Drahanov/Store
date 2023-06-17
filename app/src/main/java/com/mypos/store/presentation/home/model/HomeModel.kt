package com.mypos.store.presentation.home.model

import com.mypos.store.domain.articles.model.ArticleEntity
import com.mypos.store.presentation.base.model.UiEvent
import com.mypos.store.presentation.base.model.UiSideEffect
import com.mypos.store.presentation.base.model.UiState

class HomeModel {
    data class HomeUiState(
        val isLoading: Boolean = false,
        val articles: List<ArticleEntity> = emptyList()
    ) : UiState

    sealed class HomeUiEvent : UiEvent {
        object LoadArticles : HomeUiEvent()
    }

    class HomeUiEffect : UiSideEffect
}