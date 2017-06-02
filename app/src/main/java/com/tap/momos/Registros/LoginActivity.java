package com.tap.momos.Registros;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tap.momos.MainActivity;
import com.tap.momos.PrincipalActivity;
import com.tap.momos.R;

public class LoginActivity extends AppCompatActivity {
    //UI
    private Button Login;
    private EditText Email;
    private EditText Contrasena;
    private TextView NombreTec;

    private ProgressDialog progressDialog;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAutheListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login = (Button) findViewById(R.id.login_button);
        Email = (EditText) findViewById(R.id.login_correo);
        Contrasena = (EditText) findViewById(R.id.login_contrasena);
        NombreTec = (TextView) findViewById(R.id.login_nombreTec);
        progressDialog = new ProgressDialog(this);



        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            PantallaPrincipal();
        }
    }

    public void noRegistrado(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        String email = Email.getText().toString().trim();
        String password = Contrasena.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Por favor ingrese su correo electrónico", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor ingrese su contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Entrando");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            //correoverficado();
                            Toast.makeText(LoginActivity.this, "Logeado exitosamente", Toast.LENGTH_SHORT).show();
                            PantallaPrincipal();
                        } else {
                            Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void correoverficado() {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            if(user.isEmailVerified()){
                PantallaPrincipal();  
            }else {
                Toast.makeText(LoginActivity.this, "Verifique su correo electronico", Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
            }
        }
        
    }

    private void PantallaPrincipal() {
        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void olvideConrasena(View view) {
        Toast.makeText(LoginActivity.this, " :,(", Toast.LENGTH_SHORT).show();
    }
}
