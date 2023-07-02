package com.mypos.store.presentation.refactor.model;

import com.mypos.store.domain.articles.model.ArticleEntity;
import com.mypos.store.domain.util.result.Result;

import java.util.List;

public interface HomeEventsCallback {
    void onFetched(List<ArticleEntity> articles);

    void onArticleAdded(ArticleEntity article);

    void onArticleDeleted(ArticleEntity article);

    void onArticleUpdated(ArticleEntity article);

    void onError(String message);
}
