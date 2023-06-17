package com.mypos.store.presentation.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.viewModels
import com.mypos.store.R
import com.mypos.store.databinding.FragmentHomeBinding
import com.mypos.store.presentation.base.viewmodel.observeIn
import com.mypos.store.presentation.home.model.HomeModel
import com.mypos.store.presentation.home.viewmodel.HomeViewModel
import com.mypos.store.presentation.ui.articles.ArticleItem
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
        binding.articlesList.apply {
            setContent {
                val state = viewModel.uiState.collectAsState().value
                Column() {
                    ArticleItem()
                    ArticleItem()
                    ArticleItem()
                    ArticleItem()
                    ArticleItem()
                    ArticleItem()
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}