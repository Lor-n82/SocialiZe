package com.example.socialize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class Registro extends AppCompatActivity {

    private Button registro;
    private EditText mail, passwd;
    private FirebaseAuth autenticacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        autenticacion = FirebaseAuth.getInstance();
        asociarViews();
    }

    private List<String> cargarDatos(List<String> lista) {
        lista.add(mail.getText().toString().trim());
        lista.add(passwd.getText().toString().trim());

        if(TextUtils.isEmpty(lista.get(0))){
            Toast.makeText(this,"Introduce un mail",Toast.LENGTH_LONG).show();
        }

        if(TextUtils.isEmpty(lista.get(1))){
            Toast.makeText(this,"Introduce una contraseña",Toast.LENGTH_LONG).show();
        }

        return lista;
    }

    private void crearUsuario(String mail, String passwd) {
        autenticacion.createUserWithEmailAndPassword(mail,passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AAA", "createUserWithEmail:correcto");
                            FirebaseUser usuario = autenticacion.getCurrentUser();
                            //updateUI(usuario);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AAA", "createUserWithEmail:falló", task.getException());
                            Toast.makeText(getApplicationContext(),"Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuario = autenticacion.getCurrentUser();
        //updateUI(usuario);
    }

    /**
     * Fnc asociarViews.
     */
    private void asociarViews() {
        registro =  findViewById(R.id.botonPantallaRegistro);
        mail = findViewById(R.id.editTextMail);
        passwd = findViewById(R.id.editTextPasswd);
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
        if(registro.getId() == botonClave.getId()){
            List<String> lista = new ArrayList<>();
            lista = cargarDatos(lista);
            crearUsuario(lista.get(0), lista.get(1));
        }
    }
}
