package com.tap.momos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tap.momos.Registros.LoginActivity;

public class MainActivity extends AppCompatActivity {

//    private BottomNavigationView bottomNavigationItemView;
//    private int  PICK_IMAGE = 172;
//    //Firebase
//    private FirebaseAuth firebaseAuth;
//    private FirebaseAuth.AuthStateListener firebaseAuthListener;
//    private FirebaseDatabase firebaseDatabase;
//    private DatabaseReference databaseReference;
//    private Uri filePath;
//    private StorageReference storageReference;
//    private ProgressDialog progressDialog;
//    private Fragment fragmentoGenerico;
//    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
//
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference();
//        progressDialog = new ProgressDialog(this);
//
//        fragmentoGenerico = null;
//        fragmentManager = getSupportFragmentManager();
//        seleccionarItem(bottomNavigationItemView.getMenu().getItem(0));
//
//        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(MenuItem item) {
//                if(item.getItemId() == R.id.inicio_item ){
//                    Toast.makeText(MainActivity.this, "Inicio", Toast.LENGTH_SHORT).show();
//                    fragmentoGenerico = new FragmentoPerfil();
//                }else if(item.getItemId() == R.id.camara_item ){
//                   // showFileChooser();
//                    Toast.makeText(MainActivity.this, "Galeria", Toast.LENGTH_SHORT).show();
//                    fragmentoGenerico = new FragmentoCamara();
//                }else if(item.getItemId() == R.id.profile_item ){
//                    Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
//                   // subirFotodeperfil();
//                }
//                if (fragmentoGenerico != null) {
//                    fragmentManager
//                            .beginTransaction()
//                            .replace(R.id.contenedor_principal, fragmentoGenerico)
//                            .commit();
//                }
//                return true;
//            }
//        });
//
//        firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        if (user == null){
//            PantallaLogin();
//        }
//    }
//
//    private void PantallaLogin() {
//        Intent intent = new Intent(this, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }
//
//
//    public boolean seleccionarItem(MenuItem item) {
//        // Handle navigation view item clicks here.
//        Fragment fragmentoGenerico = null;
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        if(item.getItemId() == R.id.inicio_item ){
//            Toast.makeText(MainActivity.this, "Inicio", Toast.LENGTH_SHORT).show();
//            fragmentoGenerico = new FragmentoPerfil();
//        }else if(item.getItemId() == R.id.camara_item ){
//            showFileChooser();
//            Toast.makeText(MainActivity.this, "Galeria", Toast.LENGTH_SHORT).show();
//        }else if(item.getItemId() == R.id.profile_item ){
//            Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
//            subirFotodeperfil();
//        }
//        if (fragmentoGenerico != null) {
//            fragmentManager
//                    .beginTransaction()
//                    .replace(R.id.contenedor_principal, fragmentoGenerico)
//                    .commit();
//        }
//        return true;
//    }
//
//    private void showFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Foto de perfil"), PICK_IMAGE);
//    }
//    private void subirFotodeperfil() {
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        if (user == null) {
//            if (filePath != null) {
//                progressDialog.setTitle("Subiendo meme");
//                progressDialog.show();
//                StorageReference riversRef = storageReference.child("images/").child("memes/");
//                riversRef.putFile(filePath)
//                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                progressDialog.dismiss();
//                                Toast.makeText(MainActivity.this, "Meme subido exitosamente", Toast.LENGTH_SHORT).show();
//                                //fotodePerfil();
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                progressDialog.dismiss();
//                                Toast.makeText(MainActivity.this, "El cambio ha fallado", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                                //displaying percentage in progress dialog
//                                progressDialog.setMessage("Subiendo " + ((int) progress) + "%...");
//                            }
//                        });
//            } else {
//                Toast.makeText(MainActivity.this, "Su meme no se ha subido exitosamentee", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode != Activity.RESULT_CANCELED) {
//            if (requestCode == PICK_IMAGE) {
//                Uri selectedImageUri = data.getData();
//            }
//        }
  }
}

