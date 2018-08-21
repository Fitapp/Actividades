package com.example.valen.fitapp.app;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valen.fitapp.MainActivity;
import com.example.valen.fitapp.PassaLogin;
import com.example.valen.fitapp.R;
import com.example.valen.fitapp.api.ClienteAtleta;
import com.example.valen.fitapp.api.ClientePT;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Registo extends AppCompatActivity {
    private EditText et_nome, et_email, et_password, et_cpassword , et_morada;
    private String nome, email, password, cpassword , morada;
    Button registobutton;

    private FirebaseAuth auth;
    private DatabaseReference referencia_raiz;
    private FirebaseUser utilizador_firebase ;

    private TextView mostraData;
    private DatePickerDialog.OnDateSetListener onDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);
        et_nome = (EditText) findViewById(R.id.nome_registo);
        et_email = (EditText) findViewById(R.id.email_registo);
        et_password = (EditText) findViewById(R.id.password_registo);
        et_cpassword = (EditText) findViewById(R.id.cpassword_registo);
        et_morada = findViewById(R.id.morada_registo);
        registobutton = (Button) findViewById(R.id.registo_button);
        mostraData = (TextView) findViewById(R.id.data_nascimento_registo);



        auth = FirebaseAuth.getInstance();


        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia)
            {
                mes = mes + 1;
                String data = dia + "/" + mes + "/" + ano;
                mostraData.setText(data);
            }
        };


    }

    public void onClick_mostraData ( View v)
    {
        Calendar cal = Calendar.getInstance();
        int ano = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this , android.R.style.Theme_Holo_Light_Dialog_MinWidth ,onDateSetListener , ano , mes ,dia   );

        dialog.show();
    }

    public void openactivity_PassaLogion()
    {
        auth.signOut();
        finish();
        startActivity(new Intent(Registo.this, PassaLogin.class));
        //Toast.makeText(getApplicationContext(), "Email não registado" , Toast.LENGTH_SHORT).show();
    }



    public void registar(View v){
        intialize();
        if (!validate()){
            Toast.makeText(this, "ERRO NO REGISTO", Toast.LENGTH_SHORT).show();
        }
        else {
            create_user(v , 1);
        }
    }

    private boolean validate() {
        boolean valid = true;
        if (nome.isEmpty()|| nome.length() > 32) {
            et_nome.setError("Coloque um Nome Valido!");
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
        if (morada.isEmpty()) {
            et_morada.setError("Confirme a Password!");
            valid = false;
        }
        return valid;

    }
    private void intialize(){
        nome = et_nome.getText().toString().trim();
        email = et_email.getText().toString().trim();
        password = et_password.getText().toString().trim();
        cpassword = et_cpassword.getText().toString().trim();
        morada = et_morada.getText().toString().trim();
    }



    public void create_user(final View v , final int tipoCliente)
    {
        auth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {

                if(task.isSuccessful())
                {
                    utilizador_firebase = auth.getCurrentUser();

                    if (tipoCliente == 1) {
                        referencia_raiz = FirebaseDatabase.getInstance().getReference().child("Utilizadores").child("Cliente");


                        ClienteAtleta cliente_novo = new ClienteAtleta(nome, email, password);
                        //Crio um novo utilizador com os dados inseridos pelo utilizador na página Registo

                        referencia_raiz.child(utilizador_firebase.getUid()).setValue(cliente_novo)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            //Toast.makeText(getApplicationContext() , "LEITEEEE" , Toast.LENGTH_SHORT).show();
                                            EnviaMaildeVerficacao();

                                            //Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                            //startActivity(intent);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "NADA GUARDADO", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

                    if (tipoCliente == 2) {
                        referencia_raiz = FirebaseDatabase.getInstance().getReference().child("Utilizadores").child("PersonalTrainer");


                        ClientePT cliente_novo = new ClientePT(nome, email, password);
                        //Crio um novo utilizador com os dados inseridos pelo utilizador na página Registo

                        referencia_raiz.child(utilizador_firebase.getUid()).setValue(cliente_novo)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                             Toast.makeText(getApplicationContext() , "Valores guardados na base de dados" , Toast.LENGTH_SHORT).show();
                                             openactivity_PassaLogion();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "NADA GUARDADO", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

                }


                else
                {
                    Toast.makeText(getApplicationContext() , "NÃO DEU CARALHO" , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void EnviaMaildeVerficacao(){
        FirebaseUser firebaseUser =auth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        Toast.makeText(Registo.this, "Registado! Verifique o mail enviado.", Toast.LENGTH_SHORT).show();
                        auth.signOut();
                        finish();
                        startActivity(new Intent(Registo.this, MainActivity.class));
                    } else{
                        Toast.makeText(Registo.this,"Falha no envio do mail de verificação!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    //public void setFirebaseAuth(FirebaseAuth firebaseAuth) {
    //  this.auth = firebaseAuth;
    //}
}