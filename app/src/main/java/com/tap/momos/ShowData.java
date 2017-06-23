package com.tap.momos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by luisf on 01/06/2017.
 */
public class ShowData extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private LinearLayoutManager mLayoutManager;
    private FirebaseRecyclerAdapter<ShowDataItems, ShowDataViewHolder> mFirebaseAdapter;

    public ShowData() {
// Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_datos);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("User_Details");

        recyclerView = (RecyclerView)findViewById(R.id.mostrar_datos_ly);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowData.this));
        Toast.makeText(ShowData.this, "Cargando...", Toast.LENGTH_SHORT).show();



    }


    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<ShowDataItems, ShowDataViewHolder>
                (ShowDataItems.class, R.layout.item_data, ShowDataViewHolder.class, myRef)

        {

            public void populateViewHolder(final ShowDataViewHolder viewHolder, ShowDataItems model, final int position) {
                viewHolder.Image_URL(model.getImage_URL());
                viewHolder.Image_Title(model.getImage_Title());



                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ShowData.this);
                        builder.setMessage("Deseas eliminar este elemento?").setCancelable(false)
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int selectedItems = position;
                                        mFirebaseAdapter.getRef(selectedItems).removeValue();
                                        mFirebaseAdapter.notifyItemRemoved(selectedItems);
                                        recyclerView.invalidate();
                                        onStart();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.setTitle("Confimar");
                        dialog.show();
                    }
                });


            }
        };

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mFirebaseAdapter);


    }



    //View Holder For Recycler View
    public static class ShowDataViewHolder extends RecyclerView.ViewHolder {
        private final TextView image_title;
        private final ImageView image_url;

        public ShowDataViewHolder(final View itemView) {
            super(itemView);

            image_url = (ImageView) itemView.findViewById(R.id.img_meme);
            image_title = (TextView) itemView.findViewById(R.id.texto_meme);
        }

        private void Image_Title(String title) {
            image_title.setText(title);
        }

        private void Image_URL(String title) {
            Glide.with(itemView.getContext())
                    .load(title)
                    .crossFade()
                    .thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image_url);
        }
    }


}