package com.example.valen.fitapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.valen.fitapp.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {
    private Button loginbutton,registerbutton;
    ProgressBar progressbutton;
    private EditText et_email, et_password;
    private String email, password ;

    private FirebaseAuth auth;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        loginbutton = findViewById(R.id.login_button);
        registerbutton =  findViewById(R.id.register_button);
        progressbutton =  findViewById(R.id.login_progress);
        progressbutton.setVisibility(View.INVISIBLE);

        et_email = (EditText) findViewById(R.id.email_space);
        et_password = (EditText) findViewById(R.id.password_space);

        auth = FirebaseAuth.getInstance();

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
                Login();
            }
        });

    }

    public void openactivity_registo()
    {
        Intent intent = new Intent(this, Registo.class);
        startActivity(intent);

    }
//////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////

//Funções que Determinam o Login

    public void Login(){
        intialize();
        if (!validate()){
            Toast.makeText(this, "Erro no Login", Toast.LENGTH_SHORT).show();
        }
        else {
            faz_login();
        }
    }

    public boolean validate() {
        boolean valid = true;

        if (email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("Email Inválido!");
            valid = false;
        }
        if (password.isEmpty()) {
            et_password.setError("Coloque uma Password!");
            valid = false;
        }
        return valid;
    }

    public void intialize()
    {
        email = et_email.getText().toString().trim();
        password = et_password.getText().toString().trim();
    }

    public void faz_login()
    {
        auth.signInWithEmailAndPassword(email ,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Login executado com sucesso" , Toast.LENGTH_SHORT).show();
                    finish();
                    Intent i = new Intent(getApplicationContext() , ProfileActivity.class);
                    startActivity(i);
                }
                else
                    Toast.makeText(getApplicationContext(), "Não conseguiu fazer log in" , Toast.LENGTH_SHORT).show();
            }
        });
    }


}