package com.mypos.store.presentation.confirmation.viewmodel

import com.mypos.store.domain.cart.repository.CartRepository
import com.mypos.store.presentation.base.viewmodel.BaseViewModel
import com.mypos.store.presentation.confirmation.model.ConfirmationModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : BaseViewModel<ConfirmationUiState, ConfirmationUiEvent, ConfirmationEffect>(
    ConfirmationUiState()
) {
    override suspend fun handleEvent(event: ConfirmationUiEvent) {
        when (event) {
            ConfirmationUiEvent.ConfirmOrder -> {
                cartRepository.clearCart()
            }
        }

    }
}