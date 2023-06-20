package com.mypos.store.presentation.addNew.model

import android.graphics.Bitmap
import com.mypos.store.domain.articles.model.ArticleEntity
import com.mypos.store.presentation.base.model.UiEvent
import com.mypos.store.presentation.base.model.UiSideEffect
import com.mypos.store.presentation.base.model.UiState

class AddNewModel {
    data class AddNewUiState(
        val isLoading: Boolean = false,
        var image: Bitmap? = null,
        val isEditMode: Boolean = false,
        val article: ArticleEntity? = null
    ) : UiState

    sealed class AddNewUiEvent : UiEvent {
        data class AddNew(
            val title: String,
            val price: Double,
            val shortDescription: String,
            val fullDescription: String,
        ) : AddNewUiEvent()

        data class ImageLoaded(var data: Bitmap) : AddNewUiEvent()
        data class StartEditMode(val id: Int) : AddNewUiEvent()
    }

    sealed class AddNewUiSideEffect : UiSideEffect {
        object Saved : AddNewUiSideEffect()
    }
}