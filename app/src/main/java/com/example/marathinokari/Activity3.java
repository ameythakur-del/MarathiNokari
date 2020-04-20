package com.example.marathinokari;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import Utils.resumeApi;

public class Activity3 extends AppCompatActivity implements View.OnClickListener{

    private EditText username, name;
    private EditText password;
    private Button register, login;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        register = (Button)findViewById(R.id.button3);
        login = (Button)findViewById(R.id.loginButton) ;
        username = (EditText) findViewById(R.id.username);
        name = (EditText) findViewById(R.id.name);
        password = (EditText)findViewById(R.id.password);
        register.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser =  firebaseAuth.getCurrentUser();

                if(currentUser != null){

                }
                else{

                }
            }
        };


    }

    private void registerUser() {
        final String Username = username.getText().toString().trim();
        String Password = password.getText().toString().trim();
        final String Name = name.getText().toString().trim();

        if (TextUtils.isEmpty(Username)) {
            Toast.makeText(this, "Please Enter the email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "Please Enter the password",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Name)) {
            Toast.makeText(this, "Please Enter the name",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(Username, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                                     currentUser = firebaseAuth.getCurrentUser();
                                     assert currentUser != null;
                                     final String currentUserId = currentUser.getUid();
                                     final String Username = currentUser.getEmail();
                                     Map<String, String> userObj = new HashMap<>();
                                     userObj.put("Name", Name);
                                     userObj.put("Username", Username);
                                     userObj.put("UserId", currentUserId);

                                     collectionReference.add(userObj)
                                             .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                 @Override
                                                 public void onSuccess(DocumentReference documentReference) {
                                                     documentReference.get()
                                                             .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                 @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                                                 @Override
                                                                 public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                     if(Objects.requireNonNull(task.getResult().exists())){
                                                                         String name = task.getResult()
                                                                                 .getString("Name");

                                                                         resumeApi ResumeApi = resumeApi.getInstance();
                                                                         ResumeApi.setName(name);
                                                                         Toast.makeText(Activity3.this, "Registered Successfully",Toast.LENGTH_SHORT).show();
                                                                         progressDialog.dismiss();
                                                                         finish();
                                                                         Intent intent = new Intent(Activity3.this, UploadActivity.class);
                                                                         startActivity(intent);                                                                     }
                                                                 }
                                                             });
                                                 }
                                             });



                        }
                        else {
                            Toast.makeText(Activity3.this, "Could not register...Please try again", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }
    @Override
    public void onClick(View view) {
        if(view == register)
            registerUser();
    }
}