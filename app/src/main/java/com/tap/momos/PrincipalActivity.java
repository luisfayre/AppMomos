package com.tap.momos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.tap.momos.Registros.LoginActivity;

/**
 * Created by luisf on 01/06/2017.
 */
public class PrincipalActivity extends AppCompatActivity {

    private Button subirImagen, cerrarSecion, mostrarImagen;

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
