package com.example.socialize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.socialize.menu.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Usuario mUsuario;
    private HashMap<String, String> mapa;
    private Button botonLogin, botonRegistro;
    private EditText usuario, passwd;
    private FirebaseAuth autenticacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        asociarViews();
        autenticacion = FirebaseAuth.getInstance();

        //DataBase.escribirDDBB(database, myRef, mapa);
    }

    private void loginUsuario(String usuario, String passwd) {
        autenticacion.signInWithEmailAndPassword(usuario, passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AAA", "Bienvenido.");
                            FirebaseUser usuario = autenticacion.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AAA", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Usuario o contraseña invalidos.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuario = autenticacion.getCurrentUser();
        //updateUI(currentUser);
    }

    /**
     * Fnc asociarViews
     */
    private void asociarViews() {
        botonLogin = findViewById(R.id.botonLogin);
        botonRegistro = findViewById(R.id.botonRegistrate);
        usuario = findViewById(R.id.editTextUser);
        passwd = findViewById(R.id.etUsuario);
    }

    /**
     * Fnc onClick
     * @param v
     */
    public void onClick(View v){
        Button botonClave = (Button) v;
        accionBoton(botonClave);
    }

    /**
     * Fnc Arranca actividad segun su boton
     * @param botonClave
     */
    private void accionBoton(Button botonClave) {
        if(botonLogin.getId() == botonClave.getId()){
            //PETA, HAY QUE CONTROLAR QUE LLEGUEN LOS CAMPOS INFORMADOS
            loginUsuario(usuario.getText().toString(), passwd.getText().toString());
        }
        if(botonRegistro.getId() == botonClave.getId()){
            startActivity(new Intent(this, Registro.class));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
