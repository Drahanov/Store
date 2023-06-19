package com.mypos.store.presentation.details.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.viewModels
import com.mypos.store.databinding.FragmentDetailsBinding
import com.mypos.store.presentation.base.viewmodel.observeIn
import com.mypos.store.presentation.details.model.DetailsModel
import com.mypos.store.presentation.details.viewmodel.DetailsViewModel
import com.mypos.store.presentation.ui.details.ArticleDetailsItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()
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

        initCompose()
        viewModel.setEvent(DetailsModel.DetailsUiEvent.LoadDetails(arguments?.getInt("productId")))
        return view
    }

    private fun initCompose() {
        binding.cardDetails.apply {
            setContent {
                val state = viewModel.uiState.collectAsState().value

                var amountInCart: Int? = 0
                amountInCart = state.amountInCart[state.articleEntity?.id]
                state.articleEntity?.let {
                    ArticleDetailsItem(
                        article = it,
                        amountInCart = amountInCart ?: 0,
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
                        })
                }
            }
        }
    }

    private fun onHandleState(state: DetailsModel.DetailsUiState) {
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}