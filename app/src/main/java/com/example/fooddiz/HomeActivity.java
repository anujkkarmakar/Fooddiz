package com.example.fooddiz;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;

public class HomeActivity extends AppCompatActivity {

    private AppCompatButton signoutbtn;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    String userId;
    private AppCompatTextView email, phone, name;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        name = findViewById(R.id.name100);
        email = findViewById(R.id.email100);

        signoutbtn = findViewById(R.id.signoubtn);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        assert firebaseUser != null;
        userId = firebaseUser.getUid();

        //update the profile

        populateUserProfile();


        //sign out button
        signoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                firebaseAuth.signOut();
                //update UI
                Toast.makeText(HomeActivity.this, "Successfully sign out", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                startActivity(new Intent(HomeActivity.this, ParentActivity.class));
                finish();
            }
        });
    }

    private void populateUserProfile() {
        progressDialog.show();
        DatabaseReference reference = firebaseDatabase.getReference().child("users").child(firebaseAuth.getUid());

        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
                    if(task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        name.setText(String.valueOf(dataSnapshot.child("name").getValue()));
                        email.setText(String.valueOf(dataSnapshot.child("email").getValue()));
                        progressDialog.dismiss();
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(HomeActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(HomeActivity.this, "Could not retrieve data", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                name.setText(String.valueOf(snapshot.child("name").getValue()));
//                email.setText(String.valueOf(snapshot.child("email").getValue()));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
    }
}
