package com.mypos.store.presentation.addNew.model

import com.mypos.store.domain.articles.model.ArticleEntity
import com.mypos.store.presentation.base.model.UiEvent
import com.mypos.store.presentation.base.model.UiSideEffect
import com.mypos.store.presentation.base.model.UiState

class AddNewModel {
    data class AddNewUiState(
        val isLoading: Boolean = false,
        val articles: List<ArticleEntity> = emptyList(),
        var image: ByteArray? = null
    ) : UiState

    sealed class AddNewUiEvent : UiEvent {
        data class AddNew(
            val title: String,
            val price: Double,
            val shortDescription: String,
            val fullDescription: String
        ) : AddNewUiEvent()

        data class ImageLoaded(var data: ByteArray) : AddNewUiEvent()
    }

    class AddNewUiSideEffect : UiSideEffect
}