package com.mypos.store.presentation.addNew.viewmodel

import androidx.lifecycle.viewModelScope
import com.mypos.store.domain.articles.model.ArticleEntity
import com.mypos.store.domain.articles.repository.ArticlesRepository
import com.mypos.store.presentation.addNew.model.AddNewModel
import com.mypos.store.presentation.addNew.model.AddNewModel.*
import com.mypos.store.presentation.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddNewViewModel @Inject constructor(
    private val repository: ArticlesRepository
) : BaseViewModel<AddNewUiState, AddNewUiEvent, AddNewUiSideEffect>(
    AddNewUiState()
) {
    override suspend fun handleEvent(event: AddNewUiEvent) {
        when (event) {
            is AddNewUiEvent.AddNew -> {
                setState { copy(isLoading = true) }

                viewModelScope.launch {
                    val article = ArticleEntity(
                        name = event.title,
                        category = 0,
                        addDate = Date(),
                        shortDescription = event.shortDescription,
                        fullDescription = event.fullDescription,
                        price = event.price,
                        id = 0
                    )
                    val id = repository.addArticle(article)

                    if (uiState.value.image != null) {
                        repository.saveToInternalStorage(
                            bitmapImage = uiState.value.image!!,
                            id.toInt()
                        )
                    }

                    setState { copy(isLoading = false) }
                    setEffect(AddNewUiSideEffect.Saved)
                }
            }

            is AddNewUiEvent.ImageLoaded -> {
                setState { copy(image = event.data) }
            }
        }
    }
}