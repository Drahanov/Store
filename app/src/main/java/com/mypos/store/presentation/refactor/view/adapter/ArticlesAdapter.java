package com.mypos.store.presentation.refactor.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mypos.store.R;
import com.mypos.store.domain.articles.model.ArticleEntity;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private final List<ArticleEntity> articles;
    private final ClickListener clickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView price;
        private final TextView description;
        private final ImageView image;
        private final TextView amountInCart;
        private final View view;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.articleName);
            price = view.findViewById(R.id.articlePrice);
            description = view.findViewById(R.id.articleDescription);
            image = view.findViewById(R.id.imageView);
            amountInCart = view.findViewById(R.id.amountInCartTV);
            this.view = view;
        }

        public TextView getPrice() {
            return price;
        }

        public TextView getName() {
            return name;
        }

        public TextView getDescription() {
            return description;
        }

        public ImageView getImage() {
            return image;
        }

        public TextView getAmountInCart() {
            return amountInCart;
        }

        public View getView() {
            return view;
        }

    }

    public ArticlesAdapter(List<ArticleEntity> articles, ClickListener clickListener) {
        this.articles = articles;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.article_rv_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String price = Double.toString(articles.get(position).getPrice()) + "$";
        viewHolder.getPrice().setText(price);
        viewHolder.getName().setText(articles.get(position).getName());
        viewHolder.getAmountInCart().setText(String.valueOf(articles.get(position).getAmountInCart()));
        viewHolder.getDescription().setText(articles.get(position).getShortDescription());

        viewHolder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onArticleClick(articles.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void addNewItem(ArticleEntity article) {
        articles.add(article);
        notifyItemInserted(articles.size() - 1);
    }

    public void deleteItem(ArticleEntity article) {
        int index = articles.indexOf(article);
        articles.remove(index);
        notifyItemRemoved(index);
    }

    public void updateItem(ArticleEntity article) {
        int index = articles.indexOf(article);
        articles.set(index, article);
        notifyItemChanged(index);
    }

    public void addMultipleItems(List<ArticleEntity> articles) {
        int lastIndex = articles.size() - 1;
        this.articles.addAll(articles);
        notifyItemRangeInserted(lastIndex, articles.size());
    }
}
