package com.android.example.rentalapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

     static class CategoryViewHolder extends RecyclerView.ViewHolder{

         ImageView mImageView;
         TextView mTextView;
         SharedPreferences categoryAdapterPref;


         CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

                    itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                categoryAdapterPref = v.getContext().getSharedPreferences("category adapter position", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = categoryAdapterPref.edit().putInt("key", getAdapterPosition());
                editor.apply();

                    SharedPreferences preferences = v.getContext().getSharedPreferences("buttonChoice", Context.MODE_PRIVATE);

                    if (preferences.getBoolean("ChosenButton", true)) {

                        Intent i = new Intent(v.getContext(), GiveOnRentActivity.class);
                        {
                            if (getAdapterPosition() == 0)
                                i.putExtra("Category", "Apparels");

                            else if (getAdapterPosition() == 1)
                                i.putExtra("Category", "Footwear");

                            else if (getAdapterPosition() == 2)
                                i.putExtra("Category", "Accessories");

                            else if (getAdapterPosition() == 3)
                                i.putExtra("Category", "Books");

                            else if (getAdapterPosition() == 4)
                                i.putExtra("Category", "Gadgets");

                            else if (getAdapterPosition() == 5)
                                i.putExtra("Category", "Automobiles");
                        }
                        v.getContext().startActivity(i);
                    }

                     else {
                        Intent i = new Intent(v.getContext(), TakeOnRentActivity.class);
                         v.getContext().startActivity(i);
                    }
                }

            });
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView = itemView.findViewById(R.id.textView);

        }
    }

     CategoryAdapter(ArrayList<CategoryItem> categoryList){
        mCategoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.categories_recycler_view_items, parent, false);

        return (new CategoryViewHolder(v));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryItem currentItem = mCategoryList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView.setText(currentItem.getText());
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }
}
