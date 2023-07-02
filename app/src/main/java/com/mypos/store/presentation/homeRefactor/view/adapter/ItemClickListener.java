package com.mypos.store.presentation.homeRefactor.view.adapter;

import com.mypos.store.domain.articles.model.ArticleEntity;

public interface ItemClickListener {
    void onArticleClick(int articleId);
    void onCartActionClick(ArticleEntity article, boolean isIncreased);
}
