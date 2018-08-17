package com.example.valen.fitapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.valen.fitapp.app.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;

public class PassaLogin extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passa_login);

        auth = FirebaseAuth.getInstance();


        if (auth.getCurrentUser() != null) {
            // User is signed in (getCurrentUser() will be null if not signed in)
            Intent intent =new  Intent(this, ProfileActivity.class);
            startActivity(intent);
            finish();
        }

        else
        {
            Intent intent =new  Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}