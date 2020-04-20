package com.example.marathinokari;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import Utils.resumeApi;

public class Activity2 extends AppCompatActivity  {
    private Button button3;
    private EditText editText;
    private EditText editText2;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private Button signup;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        firebaseAuth = FirebaseAuth.getInstance();

        editText = (EditText) findViewById(R.id.username);
        editText2 = (EditText) findViewById(R.id.password);
        button3 = (Button) findViewById(R.id.button3);
        progressDialog = new ProgressDialog(this);
        signup = (Button) findViewById(R.id.loginButton);

        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), Main3Activity.class));
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == signup) {
                    startActivity(new Intent(Activity2.this, Activity3.class));
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(editText.getText().toString().trim(), editText2.getText().toString().trim());
            }

            private void loginUser(String username, String password) {
                progressDialog.setMessage("Loging in...");
                progressDialog.show();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(Activity2.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Activity2.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }



                else {
                    firebaseAuth.signInWithEmailAndPassword(username, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    Toast.makeText(Activity2.this, "Success", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Activity2.this, Main3Activity.class));
                                }
                            })




                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Activity2.this,"Raadaa", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });


    }
}

