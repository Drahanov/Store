package com.mypos.store.presentation.homeRefactor.view.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mypos.store.R;
import com.mypos.store.domain.articles.model.ArticleEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private final List<ArticleEntity> articles;
    private final ItemClickListener clickListener;
    private final String imagesPath;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView price;
        private final TextView description;
        private final ImageView image;
        private final TextView amountInCart;
        private final View view;
        private final ImageView addToCart;
        private final ImageView removeFromCart;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.articleName);
            price = view.findViewById(R.id.articlePrice);
            description = view.findViewById(R.id.articleDescription);
            image = view.findViewById(R.id.imageView);
            amountInCart = view.findViewById(R.id.amountInCartTV);
            addToCart = view.findViewById(R.id.addToCartImage);
            removeFromCart = view.findViewById(R.id.removeFromCartImage);
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

        public ImageView getAddToCart() {
            return addToCart;
        }

        public ImageView getRemoveFromCart() {
            return removeFromCart;
        }

    }

    public ArticlesAdapter(List<ArticleEntity> articles, ItemClickListener clickListener, String imagesPath) {
        this.articles = articles;
        this.clickListener = clickListener;
        this.imagesPath = imagesPath;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.article_rv_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        ArticleEntity article = articles.get(position);

        String price = Double.toString(article.getPrice()) + "$";
        viewHolder.getPrice().setText(price);
        viewHolder.getName().setText(article.getName());
        viewHolder.getAmountInCart().setText(String.valueOf(article.getAmountInCart()));
        viewHolder.getDescription().setText(article.getShortDescription());

        viewHolder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onArticleClick(article.getId());
            }
        });

        viewHolder.getAddToCart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onCartActionClick(article, true);
            }
        });

        viewHolder.getRemoveFromCart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onCartActionClick(article, false);
            }
        });

        Bitmap image = readImage(article.getId(), imagesPath);
        if (image != null) {
            viewHolder.getImage().setImageBitmap(image);
        }
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

    public Bitmap readImage(int id, String path) {
        File f = new File(path, id + ".jpg");
        try {
            return BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
