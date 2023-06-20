package com.mypos.store.presentation.home.view

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
                Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),  //avoid the little icon
                    onClick = {
                        try {
                            Navigation.findNavController(binding.root).navigate(
                                R.id.action_homeFragment_to_addNewFragment
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }) {
                    Icon(Icons.Default.Add, contentDescription = "add icon")
                }
            }
        }

        binding.cartButton.apply {
            setContent {
                Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),  //avoid the little icon
                    onClick = {
                        try {
                            Navigation.findNavController(binding.root).navigate(
                                R.id.action_homeFragment_to_cartFragment
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "cart icon")
                }
            }
        }
        val cw = ContextWrapper(context)
        val directory = cw.getDir("articlesImages", Context.MODE_APPEND)

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
                }, imagesDirPath = directory.absolutePath
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}