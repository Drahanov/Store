package com.mypos.store.presentation.confirmation.model

import com.mypos.store.presentation.base.model.UiEvent
import com.mypos.store.presentation.base.model.UiSideEffect
import com.mypos.store.presentation.base.model.UiState

class ConfirmationModel {
    data class ConfirmationUiState(
        val isLoading: Boolean = false,
    ) : UiState

    sealed class ConfirmationUiEvent : UiEvent {
        object ConfirmOrder: ConfirmationUiEvent()
    }

    class ConfirmationEffect : UiSideEffect
}