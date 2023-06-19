package com.mypos.store.presentation.cart.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mypos.store.databinding.FragmentCartBinding
import com.mypos.store.presentation.base.viewmodel.observeIn
import com.mypos.store.presentation.cart.model.CartModel
import com.mypos.store.presentation.cart.viewmodel.CartViewModel
import com.mypos.store.presentation.ui.cart.ArticlesList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CartFragment : Fragment() {

    private val viewModel: CartViewModel by viewModels()
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root

        initCompose()

        viewModel.uiState
            .onEach(::onHandleState)
            .observeIn(this)

        return view
    }

    private fun onHandleState(state: CartModel.CartUiState) {
        binding.buttonConfirm.text = state.total.toString()
    }

    private fun initCompose() {
        binding.articlesList.apply {
            setContent {
                val state = viewModel.uiState.collectAsState().value
                ArticlesList(state.articles, state.cart) { increase, id ->
                    viewModel.setEvent(CartModel.CartUiEvent.AddToCart(increase, id))
                }

            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}