package com.example.valen.fitapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.security.PrivateKey;

public class EsqueceuPassword extends AppCompatActivity {

   private Button RecuperarPassbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu_password);
        RecuperarPassbutton =  findViewById(R.id.RecuperarPass_button);

        RecuperarPassbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recuperar();
            }
        });
    }

    void recuperar(){

    }
}
