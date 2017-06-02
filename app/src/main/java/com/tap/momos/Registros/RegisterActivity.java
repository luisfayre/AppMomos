package com.tap.momos.Registros;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tap.momos.MainActivity;
import com.tap.momos.PrincipalActivity;
import com.tap.momos.R;

public class RegisterActivity extends AppCompatActivity {

    //UI
    private Button Registro;
    private EditText Correo;
    private EditText Contrasena;
    private EditText RepetirContra;
    private EditText Usuario;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthListener;
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Registro = (Button) findViewById(R.id.button_register);
        Correo = (EditText) findViewById(R.id.register_correo);
        Contrasena = (EditText) findViewById(R.id.register_contrasena);
        RepetirContra = (EditText) findViewById(R.id.register_contrasena_contrasena);
        Usuario = (EditText) findViewById(R.id.register_usuario);
        progressDialog = new ProgressDialog(this);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        }

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference();
       if (user != null){
            PantallaPrincipal();
       }
    }

    public void Registro(View view) {
        String email = Correo.getText().toString().trim();
        String password = Contrasena.getText().toString().trim();
        String passwordConfirm = RepetirContra.getText().toString().trim();
        String user = Usuario.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, R.string.ingrese_correo, Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(user)){
            Toast.makeText(this, R.string.ingrese_usuario, Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, R.string.ingrese_contrasena, Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(passwordConfirm)){
            Toast.makeText(this, R.string.ingrese_repetir_contrasena, Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Entrando");
        progressDialog.show();
        if(password.equals(passwordConfirm)){
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                saveUserInformation();
                                progressDialog.dismiss();
                                //confirmacion();
                                //firebaseAuth.signOut();
                                PantallaPrincipal();

                            } else {
                                progressDialog.dismiss();
                            }
                        }
                    });
        }else {
            Toast.makeText(RegisterActivity.this, R.string.no_coinciden, Toast.LENGTH_SHORT).show();
        }

    }

    private void confirmacion() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Se he enviado un mensaje de confirmacion", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Error mensaje de confirmacion", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void saveUserInformation (){
        String name = Usuario.getText().toString().trim();
        String email = Correo.getText().toString().trim();
        String location = "Locacion no establecida";

        InfoUsuario informacion = new InfoUsuario(name, email, location);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            databaseReference.child("Usuarios").child(user.getUid()).setValue(informacion);
            Toast.makeText(RegisterActivity.this, "Inforamaciòn guardada", Toast.LENGTH_SHORT).show();
        }
    }
    private void PantallaPrincipal() {
        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        if (res_id == android.R.id.home) {
            super.onBackPressed();
        }
        return true;
    }
}
