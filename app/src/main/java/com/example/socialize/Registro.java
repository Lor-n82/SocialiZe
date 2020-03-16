package com.example.socialize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.socialize.menu.DataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Registro extends AppCompatActivity {

    private Button registro;
    private EditText mail, passwd;
    private FirebaseAuth autenticacion;
    private HashMap<String, String> mapaUsuario;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        autenticacion = FirebaseAuth.getInstance();
        asociarViews();
    }

    /**
     * Llena el Hasmap con los datos de usuario
     * @param mapa
     * @param lista
     */
    private HashMap<String, String> llenarMap(HashMap<String, String> mapa, List<String> lista) {
        mapa = new HashMap<>();
        mapa.put("nodoPadre","usuario");
        mapa.put("clave","mail");
        mapa.put("valor",lista.get(0));
        return mapa;
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

    /**
     * Fnc crearUsuario.
     * Registra un usuario si no existe en BBDD
     * @param mail
     * @param passwd
     */
    private void crearUsuario(String mail, String passwd) {
        autenticacion.createUserWithEmailAndPassword(mail,passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser usuario = autenticacion.getCurrentUser();
                            volver(usuario);
                        } else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(), "El usuario ya existe.",
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                // If sign in fails, display a message to the user.
                                Log.w("error", "ha fallado el registro", task.getException());
                                Toast.makeText(getApplicationContext(), "Fallo el registro.",
                                        Toast.LENGTH_SHORT).show();
                                volver(null);
                            }
                        }
                }
        });
    }

    private void volver(FirebaseUser usuario) {
        startActivity(new Intent(this, MainActivity.class));
    }

    /**
     * Fnc asociarViews.
     */
    private void asociarViews() {
        registro =  findViewById(R.id.botonPantallaRegistro);
        mail = findViewById(R.id.editTextMail);
        passwd = findViewById(R.id.editTextPasswd);

        mail.setText("");
        passwd.setText("");
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
            mapaUsuario = llenarMap(mapaUsuario, lista);
            DataBase.escribirDDBB(database, myRef, mapaUsuario);
            crearUsuario(lista.get(0), lista.get(1));
        }
    }
}
