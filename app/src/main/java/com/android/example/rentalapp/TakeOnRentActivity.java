package com.android.example.rentalapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


public class TakeOnRentActivity extends AppCompatActivity {

    private ProductAdapter adapter;
    Query query;
    String TAG = "Debug app";
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_on_rent);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.products_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseFirestore db;
        CollectionReference reference;

        db = FirebaseFirestore.getInstance();
        reference = db.collection("products");


        SharedPreferences categoryAdapterPref = this.getSharedPreferences("category adapter position", Context.MODE_PRIVATE);

            if (categoryAdapterPref.getInt("key", 0) == 0)
                query = reference.whereEqualTo("category", "Apparels");

            else if (categoryAdapterPref.getInt("key", 0) == 1)
                query = reference.whereEqualTo("category", "Footwear");

            else if (categoryAdapterPref.getInt("key", 0) == 2)
                query = reference.whereEqualTo("category", "Accessories");

            else if (categoryAdapterPref.getInt("key", 0) == 3)
                query = reference.whereEqualTo("category", "Books");

            else if (categoryAdapterPref.getInt("key", 0) == 4)
                query = reference.whereEqualTo("category", "Gadgets");

            else if (categoryAdapterPref.getInt("key", 0) == 5)
                query = reference.whereEqualTo("category", "Automobiles");


            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot querySnapshots) {
                    if (querySnapshots.isEmpty()) {
                        Log.d(TAG, "List empty");
                    } else {
                        Log.d(TAG, "List retrieved");
                    }
                }
            });
        FirestoreRecyclerOptions<product> options = new FirestoreRecyclerOptions.Builder<product>()
                .setQuery(query, product.class)
                .build();


        adapter = new ProductAdapter(options);
        recyclerView.setAdapter(adapter);


        }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

}
