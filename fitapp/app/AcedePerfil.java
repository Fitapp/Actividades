package com.example.valen.fitapp.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.valen.fitapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AcedePerfil extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private Uri resultUri;
    private Button savebutton;

    private EditText NomePerfil;
    private ImageView ImagemPerfil;
    private String userID;
    private String mURLimagemPerfil;
    private String mName;

    private FirebaseAuth auth;
    private DatabaseReference CustomerDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acede_perfil);

        ImagemPerfil = (ImageView) findViewById(R.id.image_Button);
        savebutton = (Button) findViewById(R.id.butao_guardar);
        NomePerfil = (EditText) findViewById(R.id.Nome_Perfil);

        auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();
        CustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Utilizadores").child("Cliente").child(userID);

        getUserInfo();

        ImagemPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  AbrirGaleria();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardaInfoUser();

            }
        });


    }

    private void getUserInfo(){
        CustomerDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                    if(map.get("nome")!=null){
                        mName = map.get("nome").toString();
                        NomePerfil.setText(mName);
                    }

                    if(map.get("ImagemPerfilUrl")!=null){
                        mURLimagemPerfil = map.get("ImagemPerfilUrl").toString();
                        Glide.with(getApplication()).load(mURLimagemPerfil).into(ImagemPerfil);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private void GuardaInfoUser(){
        //Guarda a imagem - falta guarda o nome

        mName = NomePerfil.getText().toString();
        Map userInfo = new HashMap();


        userInfo.put("nome", mName);

        CustomerDatabase.updateChildren(userInfo);

        if(resultUri!=null){
            StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile_Images").child(userID);
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filePath.putBytes(data);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                    return;
                }
            });

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    Map newImage = new HashMap();
                    newImage.put("ImagemPerfilUrl", downloadUrl.toString());
                    CustomerDatabase.updateChildren(newImage);

                    finish();
                    return;
                }
            });

        }else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == Activity.RESULT_OK && requestCode == 1 && data!=null){
            final Uri imagemUri = data.getData();
            resultUri=imagemUri;
            ImagemPerfil.setImageURI(resultUri);

        }

    }

}