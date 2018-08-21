package com.example.valen.fitapp.app;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.valen.fitapp.MainActivity;
import com.example.valen.fitapp.PassaLogin;
import com.example.valen.fitapp.R;
import com.facebook.CallbackManager;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import static com.example.valen.fitapp.R.layout.activity_main;
import static com.example.valen.fitapp.R.layout.activity_pt_login;

public class PtLogin extends AppCompatActivity {
    private Button loginbuton;
    private ProgressBar progressbutton;
    private EditText et_email, et_password;
    private String email, password  ;
    private TextView esqueceupassword;

    private FirebaseAuth auth;
    private FirebaseUser user;

    private DatabaseReference referencia_raiz;





    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_pt_login);


        loginbuton = findViewById(R.id.login_button);
        progressbutton =  findViewById(R.id.login_progress);
        progressbutton.setVisibility(View.INVISIBLE);

        esqueceupassword = (TextView) findViewById(R.id.esqueceu_password_text);

        et_email = (EditText) findViewById(R.id.email_space);
        et_password = (EditText) findViewById(R.id.password_space);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();



    }

//////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////

//Neste espaço estão as chamadas para novas actividades .






    public void openactivity_esqueceupassword(View v)
    {
        Intent intent = new Intent(this , EsqueceuPassword.class);
        startActivity(intent);

    }

//////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////

//Funções que Determinam o Login a partir da base de dados

    public void Login(View v){
        intialize();

        if (!validate()){
            Toast.makeText(this, "Erro no Login", Toast.LENGTH_SHORT).show();
        }
        else
            faz_login();




    }

    private boolean validate() {
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

    private void intialize()
    {
        email = et_email.getText().toString().trim();
        password = et_password.getText().toString().trim();
        //Toast.makeText(this, "O mail ficou assim" + email, Toast.LENGTH_SHORT).show();
    }

    private void faz_login()
    {
        Log.d("ARROZ " , "FAZ_LOGIN");
        auth.signInWithEmailAndPassword(email ,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            FirebaseUser firebaseUser = auth.getInstance().getCurrentUser();
                            referencia_raiz = FirebaseDatabase.getInstance().getReference().child("Utilizadores").child("PersonalTrainer").child(firebaseUser.getUid());

                            referencia_raiz.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot)
                                {
                                    if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0)
                                    {
                                        startActivity(new Intent(PtLogin.this, PtMenu.class));
                                    }
                                    else {
                                        auth.signOut();
                                        finish();
                                        startActivity(new Intent(PtLogin.this, PassaLogin.class));
                                        Toast.makeText(getApplicationContext(), "Email não registado" , Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError)
                                {
                                }
                            });
                        }

                        else
                            Toast.makeText(getApplicationContext(), "Não conseguiu fazer login" , Toast.LENGTH_SHORT).show();
                    }
                });
    }



}
