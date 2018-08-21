package com.example.valen.fitapp;

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

import com.example.valen.fitapp.api.ClienteLogin;
import com.example.valen.fitapp.app.EsqueceuPassword;
import com.example.valen.fitapp.app.ProfileActivity;
import com.example.valen.fitapp.app.PtMenu;
import com.example.valen.fitapp.app.Registo;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import static com.example.valen.fitapp.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {
    private Button loginbuton,registerbutton;
    ProgressBar progressbutton;
    private EditText et_email, et_password;
    private String email, password , d_login ;
    private LoginButton loginfbbutton;
    private TextView esqueceupassword;

    private DatabaseReference referencia_raiz;

    private FirebaseAuth auth;
    private FirebaseUser user;
    CallbackManager callbackManager;




    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);


        loginbuton = findViewById(R.id.login_button);
        registerbutton =  findViewById(R.id.register_button);
        progressbutton =  findViewById(R.id.login_progress);
        progressbutton.setVisibility(View.INVISIBLE);

        esqueceupassword = (TextView) findViewById(R.id.esqueceu_password_text);

        et_email = (EditText) findViewById(R.id.email_space);
        et_password = (EditText) findViewById(R.id.password_space);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();





        if (user == null)
        {
            //setContentView(R.layout.activity_main);
            //FacebookSdk.sdkInitialize(getApplicationContext());
            loginfbbutton = (LoginButton) findViewById(R.id.login_fb_button);

            callbackManager = CallbackManager.Factory.create();
            loginfbbutton.setReadPermissions(Arrays.asList("email"));
        }
        else
        {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }
    }

//////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////

//Neste espaço estão as chamadas para novas actividades .


    public void openactivity_registo(View v)
    {
        Intent intent = new Intent(this, Registo.class);
        startActivity(intent);

    }

    public void openactivity_perfil(View v)
    {
        Intent intent = new Intent(this , ProfileActivity.class);
        startActivity(intent);

    }

    public void openactivity_esqueceupassword(View v)
    {
        Intent intent = new Intent(this , EsqueceuPassword.class);
        startActivity(intent);

    }

    public void openartivity_menuPT(View v)
    {
        Intent intent = new Intent(this , PtMenu.class);
        startActivity(intent);
    }

    public void openactivity_PassaLogion()
    {
        auth.signOut();
        finish();
        startActivity(new Intent(MainActivity.this, PassaLogin.class));
        //Toast.makeText(getApplicationContext(), "Email não registado" , Toast.LENGTH_SHORT).show();
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
        //Toast.makeText(this, "O mail ficou assim" + email, Toast.LENGTH_SHORT).show();
    }

    public void faz_login()
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
                             //Toast.makeText(getApplicationContext(), "Login executado com sucesso" , Toast.LENGTH_SHORT).show();
                            // finish();
                            CheckMaildeVerificacao();
                            }

                        else
                            Toast.makeText(getApplicationContext(), "Não conseguiu fazer login" , Toast.LENGTH_SHORT).show();
                    }
                });
    }


//////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////

//Funções do Login pelo facebook

    public void buttonLoginFb(final View v)
    {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                handleFacebookToken(loginResult.getAccessToken() , v);

            }

            @Override
            public void onCancel()
            {
                Toast.makeText(getApplicationContext() , "Utilizador cancelou" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error)
            {
                Toast.makeText(getApplicationContext() ,error.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleFacebookToken(AccessToken accessToken ,final View v)
    {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    FirebaseUser myuser = auth.getCurrentUser();
                    openactivity_perfil(v);


                }
                else
                {
                    Toast.makeText(getApplicationContext() , "Utilizador cancelou" , Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode , resultCode ,data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void CheckMaildeVerificacao() {
        FirebaseUser firebaseUser = auth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        referencia_raiz = FirebaseDatabase.getInstance().getReference().child("Utilizadores").child("Cliente").child(firebaseUser.getUid());

        referencia_raiz.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                /*String teste = dataSnapshot.child("mail").getValue().toString();
                Log.d("Teste" , "O mail")*/
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0)
                {
                    Toast.makeText(getApplicationContext(), "" , Toast.LENGTH_SHORT).show();
                    }
                else {
                    openactivity_PassaLogion();
                }

                }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });




        Log.d("Onde para ", "Antes do decide " + emailflag);



        if (emailflag)
        {
            finish();
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        }

            else {
            finish();
            Toast.makeText(this, "Verifique o Email!", Toast.LENGTH_SHORT).show();
            auth.signOut();
            }

    }
    }
