package com.example.valen.fitapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registo extends AppCompatActivity {
    private EditText et_name, et_email, et_password, et_cpassword;
    private String name, email, password, cpassword;
    Button registobutton;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);
        et_name = (EditText) findViewById(R.id.name_space);
        et_email = (EditText) findViewById(R.id.email_space);
        et_password = (EditText) findViewById(R.id.password_space);
        et_cpassword = (EditText) findViewById(R.id.cpassword_space);
        registobutton = (Button) findViewById(R.id.registo_button);
        registobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registar();
            }
        });

        auth = FirebaseAuth.getInstance();

    }
    public void registar(){
        intialize();
        if (!validate()){
            Toast.makeText(this, "ERRO NO REGISTO", Toast.LENGTH_SHORT).show();
        }
        else {
            create_user();
       }
    }
   // public void Registo_success(){
        //COLOCAR OS INPUTS PARA A FIREBASE
  //  }
    public boolean validate() {
        boolean valid = true;
        if (name.isEmpty()|| name.length() > 32) {
            et_name.setError("Coloque um Nome Valido!");
            valid = false;
        }
        if (email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("Email Inválido!");
            valid = false;
        }
        if (password.isEmpty()) {
            et_password.setError("Coloque uma Password!");
            valid = false;
        }
        if (cpassword.isEmpty()) {
            et_cpassword.setError("Confirme a Password!");
            valid = false;
        }

    return valid;

    }
    public void intialize(){
        name = et_name.getText().toString().trim();
        email = et_email.getText().toString().trim();
        password = et_password.getText().toString().trim();
        cpassword = et_cpassword.getText().toString().trim();


        }

    public void create_user()
    {
        auth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {

                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext() , "Utilizador criado" , Toast.LENGTH_SHORT).show();
                    finish();

                    Intent i = new Intent(getApplicationContext() , ProfileActivity.class);
                    startActivity(i);
                }


                else
                {
                    Toast.makeText(getApplicationContext() , "NÃO DEU CARALHO" , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
