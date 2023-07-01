package com.mypos.store.presentation.refactor.view.adapter;

import com.mypos.store.domain.articles.model.ArticleEntity;

public interface ClickListener {
    void onArticleClick(ArticleEntity article);
}
