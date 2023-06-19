package com.mypos.store.presentation.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.mypos.store.R
import com.mypos.store.databinding.FragmentHomeBinding
import com.mypos.store.presentation.base.viewmodel.observeIn
import com.mypos.store.presentation.home.model.HomeModel
import com.mypos.store.presentation.home.viewmodel.HomeViewModel
import com.mypos.store.presentation.ui.buttons.AddNewButton
import com.mypos.store.presentation.ui.articles.ArticlesList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root


        viewModel.uiState
            .onEach(::onHandleState)
            .observeIn(this)

        viewModel.setEvent(HomeModel.HomeUiEvent.LoadArticles)

        initCompose()
        return view
    }

    private fun onHandleState(state: HomeModel.HomeUiState) {
        if (state.isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun initCompose() {
        binding.addNewButton.apply {
            setContent {
                AddNewButton() {
                    try {
                        Navigation.findNavController(binding.root).navigate(
                            R.id.action_homeFragment_to_addNewFragment
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }

        binding.cartButton.apply {
            setContent {
                AddNewButton() {
                    try {
                        Navigation.findNavController(binding.root).navigate(
                            R.id.action_homeFragment_to_cartFragment
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        binding.articlesList.apply {
            setContent {
                val state = viewModel.uiState.collectAsState().value
                ArticlesList(state.articles, state.cart, { increase, id ->
                    viewModel.setEvent(HomeModel.HomeUiEvent.AddToCart(increase, id))
                }, onClickArticle = {
                    val bundle = bundleOf("productId" to it)

                    Navigation.findNavController(binding.root).navigate(
                        R.id.action_homeFragment_to_detailsFragment, bundle
                    )
                }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}