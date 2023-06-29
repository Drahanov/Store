package com.mypos.store.presentation.refactor.viewmodel;

import androidx.lifecycle.ViewModel;

import com.mypos.store.data.articles.repository.ArticlesRepositoryImpl;
import com.mypos.store.domain.articles.model.ArticleEntity;
import com.mypos.store.domain.articles.repository.ArticlesRepository;
import com.mypos.store.domain.util.result.Result;
import com.mypos.store.presentation.refactor.model.HomeEventsCallback;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;


@HiltViewModel
public class RefactoredHomeViewModel extends ViewModel {

    private ArticlesRepository articlesRepository;
    private HomeEventsCallback callback;

    @Inject
    public RefactoredHomeViewModel(ArticlesRepository repository) {
        this.articlesRepository = repository;

    }

    public void init(HomeEventsCallback callback) {
        this.callback = callback;
    }

    public void onDestroy() {
        this.callback = null;
    }

    public void readArticles() {
        articlesRepository.readAllArticles(new ArticlesRepositoryImpl.RepositoryCallback<List<ArticleEntity>>() {
            @Override
            public void onComplete(Result<List<ArticleEntity>> result) {
                callback.onFetched(result);
            }
        });
    }

    public void addNewArticle() {
        try {
            callback.onArticleAdded();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void deleteArticle() {
        try {
            callback.onArticleDeleted();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        this.callback = null;
    }
}
