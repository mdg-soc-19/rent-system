package com.android.example.rentalapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private ArrayList<CategoryItem> mCategoryList;

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{


        public ImageView mImageView;
        public TextView mTextView;


        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView = itemView.findViewById(R.id.textView);

        }
    }

    public CategoryAdapter(ArrayList<CategoryItem> categoryList){
        mCategoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.recycler_view_items, parent, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(v);
        return (categoryViewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryItem currentItem = mCategoryList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView.setText(currentItem.getText());
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
