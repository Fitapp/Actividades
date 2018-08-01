package com.example.valen.fitapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


import com.google.firebase.auth.FirebaseAuth;

import static com.example.valen.fitapp.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {
    Button loginbutton,registerbutton;
    ProgressBar progressbutton;
    private FirebaseAuth mAuth;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        loginbutton = findViewById(R.id.login_button);
        registerbutton =  findViewById(R.id.register_button);
        progressbutton =  findViewById(R.id.login_progress);
        progressbutton.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openactivity_registo();
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbutton.setVisibility(View.VISIBLE);
                loginbutton.setVisibility(View.INVISIBLE);
            }
        });

    }

public void openactivity_registo(){
    Intent intent = new Intent(this, Registo.class);
    startActivity(intent);

}



}