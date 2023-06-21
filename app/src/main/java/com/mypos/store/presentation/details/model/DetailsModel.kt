package com.mypos.store.presentation.details.model

import com.mypos.store.domain.articles.model.ArticleEntity
import com.mypos.store.presentation.base.model.UiEvent
import com.mypos.store.presentation.base.model.UiSideEffect
import com.mypos.store.presentation.base.model.UiState

class DetailsModel {
    data class DetailsUiState(
        val isLoading: Boolean = false,
        val articleEntity: ArticleEntity? = null,
        val amountInCart: HashMap<Int, Int> = HashMap()
    ) : UiState

    sealed class DetailsUiEvent : UiEvent {
        data class LoadDetails(val id: Int?) : DetailsUiEvent()
        data class AddToCart(val shouldIncrease: Boolean, val id: Int) : DetailsUiEvent()
        data class Delete(val article: ArticleEntity) : DetailsUiEvent()
    }

    class DetailsEffect : UiSideEffect
}