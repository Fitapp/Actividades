package com.example.valen.fitapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.valen.fitapp.app.ProfileActivity;
import com.example.valen.fitapp.app.PtLogin;
import com.example.valen.fitapp.app.PtMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PassaLogin extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference referencia_raiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passa_login);

        auth = FirebaseAuth.getInstance();



        if (auth.getCurrentUser() != null) {
            // User is signed in (getCurrentUser() will be null if not signed in)
            FirebaseUser firebaseUser = auth.getCurrentUser();
            referencia_raiz = FirebaseDatabase.getInstance().getReference().child("Utilizadores").child("Cliente").child(firebaseUser.getUid());

            referencia_raiz.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                /*String teste = dataSnapshot.child("mail").getValue().toString();
                Log.d("Teste" , "O mail")*/
                    if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0)
                    {
                        startActivity(new Intent(PassaLogin.this, ProfileActivity.class));
                    }
                    else
                    {
                        startActivity(new Intent(PassaLogin.this, PtMenu.class));
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                }
            });
        }


    }

    public void onClickUtilizadores(View v)
    {
        Intent intent =new  Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickPersonalTrainers(View v)
    {
        Intent intent =new  Intent(this, PtLogin.class);
        startActivity(intent);
    }
}