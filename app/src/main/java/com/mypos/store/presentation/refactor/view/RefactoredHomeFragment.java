package com.mypos.store.presentation.refactor.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mypos.store.R;
import com.mypos.store.domain.util.result.Result;
import com.mypos.store.domain.articles.model.ArticleEntity;
import com.mypos.store.domain.articles.repository.ArticlesRepository;
import com.mypos.store.presentation.refactor.model.HomeEventsCallback;
import com.mypos.store.presentation.refactor.viewmodel.RefactoredHomeViewModel;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RefactoredHomeFragment extends Fragment implements View.OnClickListener, HomeEventsCallback {

    private View view;
    private RecyclerView recyclerView;
    private ImageButton buttonCart, buttonAddNew;
    private TextView textViewNoProducts;
    private ImageView imageViewNoProducts;
    private ProgressBar progressBar;

    RefactoredHomeViewModel viewModel;

    @Inject
    ArticlesRepository articlesRepository;

    @Inject
    ThreadPoolExecutor threadPoolExecutor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_refactored_home, container, false);
        init();
        viewModel = new ViewModelProvider(requireActivity()).get(RefactoredHomeViewModel.class);
        viewModel.init(this);

        viewModel.readArticles();
        return view;
    }


    private void init() {
        buttonCart = view.findViewById(R.id.cartButton);
        recyclerView = view.findViewById(R.id.articlesList);
        buttonAddNew = view.findViewById(R.id.addNewButton);
        textViewNoProducts = view.findViewById(R.id.textViewNoProducts);
        imageViewNoProducts = view.findViewById(R.id.imageViewNoProducts);
        progressBar = view.findViewById(R.id.progressBar);

        buttonCart.setOnClickListener(this);
        buttonAddNew.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cartButton) {
            try {
                Navigation.findNavController(view).navigate(
                        R.id.action_refactoredHomeFragment_to_cartFragment
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.addNewButton) {
            try {
                Navigation.findNavController(view).navigate(
                        R.id.action_refactoredHomeFragment_to_addNewFragment
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onFetched(Result result) {
        if (result instanceof Result.Success) {
            for (ArticleEntity article : ((Result.Success<List<ArticleEntity>>) result).data) {
                Toast.makeText(requireContext(), article.getName(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(requireContext(), "Oops, something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onArticleAdded() {
        Toast.makeText(requireContext(), "Added", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onArticleDeleted() {
        Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // to handle memory leaks
        viewModel.onDestroy();
    }
}