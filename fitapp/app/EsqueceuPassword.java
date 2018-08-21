package com.example.valen.fitapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.valen.fitapp.MainActivity;
import com.example.valen.fitapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class EsqueceuPassword extends AppCompatActivity {
    private EditText EmailPassword;
    private Button RecuperarPassbutton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu_password);

        EmailPassword = (EditText) findViewById(R.id.email_esqueceu_password);
        RecuperarPassbutton = (Button) findViewById(R.id.RecuperarPass_button);

        firebaseAuth = FirebaseAuth.getInstance();

        RecuperarPassbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recuperar();
            }
        });
    }

    void recuperar(){
        String useremail = EmailPassword.getText().toString().trim();

        if(useremail.equals("")) {
            Toast.makeText(EsqueceuPassword.this, "Coloque o seu Email",Toast.LENGTH_SHORT).show();
        }
        else {
            firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(EsqueceuPassword.this, "O para recuperar a password foi enviado!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(EsqueceuPassword.this, MainActivity.class));
                    }
                    else{
                        Toast.makeText(EsqueceuPassword.this, "Falha no envio do Mail!", Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }
    }

}