package com.tap.momos;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by Administrator on 16-03-2017.
 */

public class SubirImagen extends AppCompatActivity {

    Button select_image,upload_button;
    ImageView user_image;
    TextView title;
    public static final int READ_EXTERNAL_STORAGE = 0;
    private static final int GALLERY_INTENT = 2;
    private ProgressDialog mProgressDialog;
    private Firebase mRoofRef;
    private Uri mImageUri = null;
    private DatabaseReference mdatabaseRef;
    private StorageReference mStorage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.prueba_subir);

        Firebase.setAndroidContext(this);

        select_image = (Button)findViewById(R.id.select_image);
        upload_button = (Button)findViewById(R.id.sImg_btn_subirImagen);
        user_image = (ImageView) findViewById(R.id.sImg_imv_verImagen);
        title = (TextView) findViewById(R.id.sImg_tex_titulo);

        //Initialize the Progress Bar
        mProgressDialog = new ProgressDialog(SubirImagen.this);

        //Select Image From External Storage..
        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Check for Runtime Permission
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    //Toast.makeText(getApplicationContext(), "Call for Permission", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
                    }
                }
                else
                {
                    callgalary();
                }
            }
        });

        //Initialize Firebase Database paths for database and Storage

        mdatabaseRef = FirebaseDatabase.getInstance().getReference();
       // mRoofRef = new Firebase("https://fir-tutorial-5800f.firebaseio.com/").child("User_Details").push();  // Push will create new child every time we upload data
        mRoofRef = new Firebase("https://dulcet-cable-166621.firebaseio.com/").child("User_Details").push();  // Push will create new child every time we upload data
       // mStorage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://fir-tutorial-5800f.appspot.com/");
        mStorage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://dulcet-cable-166621.appspot.com/");




        //Click on Upload Button Title will upload to Database
        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 String mName = title.getText().toString().trim();


                if(mName.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Sin titulo", Toast.LENGTH_SHORT).show();
                    mName = "Sin titulo";
                    Firebase childRef_name = mRoofRef.child("Image_Title");
                    childRef_name.setValue(mName);

                }else{
                    Firebase childRef_name = mRoofRef.child("Image_Title");
                    childRef_name.setValue(mName);
                    Toast.makeText(getApplicationContext(), "Guardando titulo...", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }
    //Check for Runtime Permissions for Storage Access
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    callgalary();
                return;
        }
        Toast.makeText(getApplicationContext(), "...", Toast.LENGTH_SHORT).show();
    }

    //If Access Granted gallery Will open
    private void callgalary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
    }

    //After Selecting image from gallery image will directly uploaded to Firebase Database
    //and Image will Show in Image View
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            mImageUri = data.getData();
            user_image.setImageURI(mImageUri);
            StorageReference filePath = mStorage.child("User_Images").child(mImageUri.getLastPathSegment());

            mProgressDialog.setMessage("Subiendo Imagen…");
            mProgressDialog.show();

            filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUri = taskSnapshot.getDownloadUrl();  //Ignore This error


                    assert downloadUri != null;
                    mRoofRef.child("Image_URL").setValue(downloadUri.toString());

                    Glide.with(getApplicationContext())
                            .load(downloadUri)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.RESULT)
                            .into(user_image);
                    // Toast.makeText(getApplicationContext(), "Updated.", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    //displaying percentage in progress dialog
                    mProgressDialog.setMessage("Subiendo " + ((int) progress) + "%...");
                }
            });
        }
    }



}
