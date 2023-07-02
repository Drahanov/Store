package com.mypos.store.presentation.homeRefactor.model;

import com.mypos.store.domain.articles.model.ArticleEntity;

import java.util.List;

public interface HomeEventsCallback {
    void onFetched(List<ArticleEntity> articles);

    void onArticleAdded(ArticleEntity article);

    void onArticleDeleted(ArticleEntity article);

    void onArticleUpdated(ArticleEntity article);

    void onError(String message);
}
