package com.example.valen.fitapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registo extends AppCompatActivity {
    private EditText et_name, et_email, et_password, et_cpassword;
    private String name, email, password, cpassword;
    Button registobutton;

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

    }
    public void registar(){
        intialize();
        if (!validate()){
            Toast.makeText(this, "ERRO NO REGISTO", Toast.LENGTH_SHORT).show();
        }
       // else {
       //     Registo_success();
      //  }
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

}
