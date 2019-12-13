package com.android.example.rentalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

public class CategoriesActivity extends AppCompatActivity {

    private Spinner s_clothes, s_footwear, s_accessories, s_books, s_automobiles, s_gadgets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        s_clothes = (Spinner) findViewById(R.id.spin_clothes);
        s_footwear = (Spinner) findViewById(R.id.spin_Footwear);
        s_accessories = (Spinner) findViewById(R.id.spin_accessories);
        s_books = (Spinner) findViewById(R.id.spin_books);
        s_gadgets = (Spinner) findViewById(R.id.spin_gadgets);
        s_automobiles = (Spinner) findViewById(R.id.spin_automobiles);

        Intent intent = getIntent();
    }

}
