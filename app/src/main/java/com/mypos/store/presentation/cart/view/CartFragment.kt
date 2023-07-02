package com.mypos.store.presentation.cart.view

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.mypos.store.R
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

        binding.buttonConfirm.setOnClickListener {
            try {
                Navigation.findNavController(binding.root).navigate(
                    R.id.action_cartFragment_to_confirmationFragment
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return view
    }

    private fun onHandleState(state: CartModel.CartUiState) {
    }

    private fun initCompose() {
        val cw = ContextWrapper(context)
        val directory = cw.getDir("articlesImages", Context.MODE_APPEND)

        binding.articlesList.apply {
            setContent {
                val state = viewModel.uiState.collectAsState().value
                ArticlesList(
                    state.articles,
                    imagePath = directory.absolutePath,
                    cartClickListener = { increase, article ->
                        viewModel.setEvent(CartModel.CartUiEvent.AddToCart(increase, article))
                    })
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}