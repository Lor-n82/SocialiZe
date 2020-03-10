package com.example.socialize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.socialize.menu.Menu;

public class MainActivity extends AppCompatActivity {

    private Button botonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonLogin = findViewById(R.id.botonLogin);
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
            startActivity(new Intent(this, MenuNavegacion.class));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
