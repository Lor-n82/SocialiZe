package com.example.socialize.menu;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DataBase {

    public static void escribirDDBB(FirebaseDatabase database, DatabaseReference myRef, Usuario user){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(String.valueOf(user.getIdUsuario()));
        myRef.child("nombre").setValue(user.getNombre());
        myRef.child("mail").setValue(user.getMail());
        myRef.child("pass").setValue(user.getPasswd());
    }

    public static void leerDDBB(DatabaseReference myRef){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("AAA", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("AAA", "Failed to read value.", error.toException());
            }
        });
    }
}
