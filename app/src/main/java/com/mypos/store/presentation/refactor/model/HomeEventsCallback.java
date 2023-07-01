package com.mypos.store.presentation.refactor.model;

import com.mypos.store.domain.articles.model.ArticleEntity;
import com.mypos.store.domain.util.result.Result;

public interface HomeEventsCallback<T> {
    void onFetched(Result<T> articles);

    void onArticleAdded(ArticleEntity article);

    void onArticleDeleted(ArticleEntity article);

    void onArticleUpdated(ArticleEntity article);
}
