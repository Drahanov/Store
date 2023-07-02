package com.mypos.store.presentation.details.view

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.mypos.store.R
import com.mypos.store.databinding.FragmentDetailsBinding
import com.mypos.store.presentation.base.viewmodel.observeIn
import com.mypos.store.presentation.details.model.DetailsModel
import com.mypos.store.presentation.details.viewmodel.DetailsViewModel
import com.mypos.store.presentation.refactor.viewmodel.RefactoredHomeViewModel
import com.mypos.store.presentation.ui.details.ArticleDetailsItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()
    private val sharedViewModel: RefactoredHomeViewModel by activityViewModels()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.uiState
            .onEach(::onHandleState)
            .observeIn(this)

        viewModel.sideEffect
            .onEach(::onHandleEffect)
            .observeIn(this)


        initCompose()
        viewModel.setEvent(DetailsModel.DetailsUiEvent.LoadDetails(arguments?.getInt("productId")))
        return view
    }

    private fun initCompose() {
        val cw = ContextWrapper(context)
        val directory = cw.getDir("articlesImages", Context.MODE_APPEND)

        binding.cardDetails.apply {
            setContent {
                val state = viewModel.uiState.collectAsState().value

                state.articleEntity?.let {
                    ArticleDetailsItem(
                        article = it,
                        cartButtonListener = { shouldIncrease, id ->
                            viewModel.setEvent(
                                DetailsModel.DetailsUiEvent.AddToCart(
                                    shouldIncrease,
                                    id
                                )
                            )
                        }, onDeleteClick = {
                            viewModel.setEvent(DetailsModel.DetailsUiEvent.Delete(it))
                            parentFragmentManager.popBackStack()
                        }, onUpdateClick = {
                            val bundle = bundleOf("productIdEdit" to it)

                            Navigation.findNavController(binding.root).navigate(
                                R.id.action_detailsFragment_to_addNewFragment, bundle
                            )
                        }, imagePath = directory.absolutePath
                    )
                }
            }
        }
    }

    private fun onHandleState(state: DetailsModel.DetailsUiState) {
        if (state.isLoading) {
            binding.progressBar3.visibility = View.VISIBLE
        } else {
            binding.progressBar3.visibility = View.GONE
        }
    }

    private fun onHandleEffect(effect: DetailsModel.DetailsEffect) {
        when (effect) {
            is DetailsModel.DetailsEffect.NotifyDeleteItem -> {
                sharedViewModel.deleteArticle(effect.article)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}