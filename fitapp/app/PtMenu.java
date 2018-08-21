package com.example.valen.fitapp.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.valen.fitapp.MainActivity;
import com.example.valen.fitapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class PtMenu extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pt_menu);

        auth= FirebaseAuth.getInstance();

        user = auth.getCurrentUser();
    }

    public void signout_pt (View v)
    {
        auth.signOut();
        finish();
        Intent i = new Intent(this , PtLogin.class);
        startActivity(i);
    }
}
