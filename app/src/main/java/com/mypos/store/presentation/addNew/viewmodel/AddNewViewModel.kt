package com.mypos.store.presentation.addNew.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.mypos.store.domain.articles.model.ArticleEntity
import com.mypos.store.domain.articles.repository.ArticlesRepository
import com.mypos.store.presentation.addNew.model.AddNewModel.*
import com.mypos.store.presentation.base.viewmodel.BaseViewModel
import com.mypos.store.presentation.ui.articles.readImage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.FileNotFoundException
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddNewViewModel @Inject constructor(
    private val repository: ArticlesRepository,
    @SuppressLint("StaticFieldLeak") @ApplicationContext private val context: Context //can leak //fix it
) : BaseViewModel<AddNewUiState, AddNewUiEvent, AddNewUiSideEffect>(
    AddNewUiState()
) {
    override suspend fun handleEvent(event: AddNewUiEvent) {
        when (event) {
            is AddNewUiEvent.AddNew -> {
                setState { copy(isLoading = true) }
                if (uiState.value.isEditMode) {
                    viewModelScope.launch {

                        val article = uiState.value.article
                        article?.name = event.title
                        article?.fullDescription = event.fullDescription
                        article?.shortDescription = event.shortDescription
                        article?.price = event.price

                        if (uiState.value.image != null) {
                            repository.saveToInternalStorage(
                                bitmapImage = uiState.value.image!!,
                                article!!.id
                            )
                        }

                        article?.let { repository.updateArticle(it) }

                        setState { copy(isLoading = false, image = null) }
                        setEffect(AddNewUiSideEffect.Saved)

                        if (article != null)
                            setEffect(AddNewUiSideEffect.NotifyHomeNew(article))
                    }
                } else {
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


                        setState { copy(isLoading = false, image = null) }
                        setEffect(AddNewUiSideEffect.Saved)
                        setEffect(AddNewUiSideEffect.NotifyHomeNew(article.copy(id = id.toInt())))

                    }
                }
            }

            is AddNewUiEvent.ImageLoaded -> {
                setState { copy(image = event.data) }
            }

            is AddNewUiEvent.StartEditMode -> {
                setState { copy(isLoading = true) }

                repository.getArticleById(event.id).collect() {

                    val cw = ContextWrapper(context)
                    val directory = cw.getDir("articlesImages", Context.MODE_APPEND)
                    var image: Bitmap? = null

                    try {
                        image = readImage(it.id, path = directory.absolutePath)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                    setState {
                        copy(
                            article = it,
                            image = image,
                            isLoading = false,
                            isEditMode = true,
                        )
                    }
                }

            }
        }
    }
}