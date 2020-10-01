package in.ac.iitr.mdg.rentalapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import in.ac.iitr.mdg.rentalapp.Classes.product;

public class UploadsAdapter extends FirestoreRecyclerAdapter<product, UploadsAdapter.UploadsViewHolder> {

    UploadsAdapter (@NonNull FirestoreRecyclerOptions<product> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UploadsViewHolder holder, int position, @NonNull product productItem) {

        Picasso.get().load(productItem.getImage()).fit().into(holder.pImage);
        holder.pName.setText(productItem.getProductName());
        holder.pRentalPrice.setText("\u20B9" + " " + productItem.getRentalPrice());
        holder.pCategory.setText("Category: " + productItem.getCategory());

       final String id = getSnapshots().getSnapshot(position).getId();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), UploadsEditActivity.class);

                FirebaseFirestore fStore = FirebaseFirestore.getInstance();
                DocumentReference ref = fStore.collection("products").document();
                i.putExtra("Product ID", id); // passing the product ID to uploads edit activity
                v.getContext().startActivity(i);
            }
        });
      }

    @NonNull
    @Override
    public UploadsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_recycler_view_items,
                parent, false);
        return new UploadsViewHolder(v);
    }

    class UploadsViewHolder extends RecyclerView.ViewHolder {
        private ImageView pImage;
        private TextView pName, pCategory,pRentalPrice;


        private UploadsViewHolder(View itemView) {
            super(itemView);
            pImage = itemView.findViewById(R.id.image_view);
            pName = itemView.findViewById(R.id.text_view_1);
            pRentalPrice = itemView.findViewById(R.id.text_view_2);
            pCategory = itemView.findViewById(R.id.text_view_3);

        }
    }
}
