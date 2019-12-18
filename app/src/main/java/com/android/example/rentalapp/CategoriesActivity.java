package com.android.example.rentalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        ArrayList<CategoryItem> categoryItemList = new ArrayList<>();
        categoryItemList.add(new CategoryItem(R.drawable.clothes, "Apparels"));
        categoryItemList.add(new CategoryItem(R.drawable.footwear, "Footwear"));
        categoryItemList.add(new CategoryItem(R.drawable.accessories, "Accessories"));
        categoryItemList.add(new CategoryItem(R.drawable.books, "Books"));
        categoryItemList.add(new CategoryItem(R.drawable.gadgets, "Gadgets"));
        categoryItemList.add(new CategoryItem(R.drawable.automobiles, "Automobiles"));

        mRecyclerView = findViewById(R.id.categories_rv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CategoryAdapter(categoryItemList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

}
