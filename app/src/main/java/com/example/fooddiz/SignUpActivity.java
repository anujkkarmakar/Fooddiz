package com.example.fooddiz;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {

    private AppCompatEditText name100, email100, password100;
    private AppCompatButton button100;
    private AppCompatTextView google, loginPage;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name100 = findViewById(R.id.name100);
        email100 = findViewById(R.id.email100);
        password100 = findViewById(R.id.password100);
        button100 = findViewById(R.id.button100);
        google = findViewById(R.id.google);
        loginPage = findViewById(R.id.textView8);
        mAuth = FirebaseAuth.getInstance();
        int c = email100.getCurrentHintTextColor();
        Log.d("Hint color", String.format("%X", c));

        loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start the Login Activity and finish the ongoing activity
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        button100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }

    private void signUpUser() {
        String name = name100.getText().toString().trim();
        String email = email100.getText().toString().trim().toLowerCase(Locale.ROOT);
        String password = password100.getText().toString().trim().toLowerCase(Locale.ROOT);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            //get the current user and do the following
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(TAG, "createUserWithEmail:success");
                            //updateUI
                            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                            finish();
                        }
                        else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
