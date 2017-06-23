package com.tap.momos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.tap.momos.Registros.LoginActivity;

/**
 * Created by luisf on 01/06/2017.
 */
public class PrincipalActivity extends AppCompatActivity{

    private Button subirImagen, cerrarSecion, mostrarImagen;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthListener;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prueba_momos);

        subirImagen = (Button) findViewById(R.id.button_subirImg);
        mostrarImagen = (Button) findViewById(R.id.button_mostrarImg);
        cerrarSecion = (Button) findViewById(R.id.button_Salir);

        mostrarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarImagen();
            }
        });

        subirImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subirImagen();
            }
        });

//        Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse("https://drive.google.com/open?id=0Bwdo7nkGILnYZ1IyMXliRTRQS2c"));
//        startActivity(intent);
//
//        int currentVersion = android.os.Build.VERSION.SDK_INT;
//        if (currentVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
//            // Do something for lollipop and above versions
//        } else{
//            // do something for phones running an SDK before lollipop
//        }


    }

    private void mostrarImagen() {
        Intent intent = new Intent(this, ShowData.class);
        startActivity(intent);
    }

    private void subirImagen() {
        Intent intent = new Intent(this, SubirImagen.class);
        startActivity(intent);
    }

    public void salir(View view) {
        FirebaseAuth.getInstance().signOut();
        pantallaLogin();
        }

    private void pantallaLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
