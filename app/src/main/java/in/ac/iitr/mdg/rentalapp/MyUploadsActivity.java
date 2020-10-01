package in.ac.iitr.mdg.rentalapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import in.ac.iitr.mdg.rentalapp.Classes.product;

public class MyUploadsActivity extends AppCompatActivity {

    private UploadsAdapter adapter;
    BottomNavigationView bottomNavigationView;
    ProgressBar progressBar;
    Query query;
    RecyclerView recyclerView;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_uploads);

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        progressBar = findViewById(R.id.progressBar);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        setUpRecyclerView();

    }




    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.my_uploads_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        FirebaseFirestore db;
        CollectionReference reference;

        db = FirebaseFirestore.getInstance();
        reference = db.collection("products");

        auth = FirebaseAuth.getInstance();
        String userID = auth.getCurrentUser().getUid();

        Log.i("userID", userID);

        query = reference.whereEqualTo("userID", userID);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.i("TAG", "listen:error", e);

                }

                else{
                    endProgress();
                }

            }
        });


        FirestoreRecyclerOptions<product> options = new FirestoreRecyclerOptions.Builder<product>()
                .setQuery(query, product.class)
                .build();

        adapter = new UploadsAdapter(options);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    private void endProgress(){
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {

                        case R.id.nav_home:
                            startActivity(new Intent(MyUploadsActivity.this, MainActivity.class));
                            finish();
                            break;

                        case R.id.nav_profile:
                            startActivity(new Intent(MyUploadsActivity.this, MyProfileActivity.class));
                            finish();
                            break;

                        case R.id.nav_help:
                            startActivity(new Intent(MyUploadsActivity.this, HelpActivity.class));
                            finish();
                            break;

                        case R.id.nav_my_uploads:
                            finish();
                            break;

                        case R.id.nav_exit:
                            finishAffinity();
                            break;
                    }

                    return false;
                }
            };

}
