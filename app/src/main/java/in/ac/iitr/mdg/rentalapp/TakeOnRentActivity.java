package in.ac.iitr.mdg.rentalapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import in.ac.iitr.mdg.rentalapp.Classes.product;


public class TakeOnRentActivity extends AppCompatActivity {

    private ProductAdapter adapter;
    Query query;
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


            query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("TAG", "listen:error", e);
                }

            }
        });


        FirestoreRecyclerOptions<product> options = new FirestoreRecyclerOptions.Builder<product>()
                .setQuery(query, product.class)
                .build();


        adapter = new ProductAdapter(options);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

}
