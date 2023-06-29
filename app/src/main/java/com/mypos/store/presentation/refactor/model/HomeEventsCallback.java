package com.mypos.store.presentation.refactor.model;

import com.mypos.store.domain.util.result.Result;

public interface HomeEventsCallback<T> {
    void onFetched(Result<T> articles);

    void onArticleAdded();

    void onArticleDeleted();
}
