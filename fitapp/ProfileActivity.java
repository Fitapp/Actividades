package com.example.valen.fitapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth= FirebaseAuth.getInstance();

        user = auth.getCurrentUser();
    }

    public void signout (View v)
    {
        auth.signOut();
        finish();
        Intent i = new Intent(this , MainActivity.class);
        startActivity(i);
    }
}