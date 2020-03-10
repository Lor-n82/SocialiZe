package com.example.socialize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.socialize.menu.Menu;

public class MenuNavegacion extends AppCompatActivity {

    private ImageButton boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_navegacion);

       // Menu.botonPulsado(boton);

    }
}
