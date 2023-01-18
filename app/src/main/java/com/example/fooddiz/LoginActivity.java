package com.example.fooddiz;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zaag;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

// The google authentication has been removed from the current version as it has few bugs. It needs to be updated from time to time

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private AppCompatEditText email100, password100;
    private AppCompatTextView textView6, google;
    private AppCompatImageView google_sign_in_btn;
    private AppCompatButton button100;
    private GoogleSignInOptions gso;
    private GoogleApiClient googleApiClient;
    private final static int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email100 = findViewById(R.id.email100);
        password100 = findViewById(R.id.password100);
        button100 = findViewById(R.id.button100);
        mAuth = FirebaseAuth.getInstance();
        textView6 = findViewById(R.id.textView6);
        google_sign_in_btn = findViewById(R.id.google_sign_in_btn);
        google = findViewById(R.id.google);

//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                        .build();
//
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                                .build();
//
//        //Implement the onClickListener methods
//        //1. google
//        google.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                googleLogIn();
//            }
//        });

        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                finish();
            }
        });

        button100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }


//    private void googleLogIn() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == RC_SIGN_IN) {
//            assert data != null;
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
//        }
//    }
//
//    private void handleSignInResult(GoogleSignInResult result) {
//        Log.d(TAG, "handleSignInResult: " + result.isSuccess());
//
//        if(result.isSuccess()) {
//            GoogleSignInAccount acct =  result.getSignInAccount();
//            Toast.makeText(this, "You have successfully logged into your account using Google", Toast.LENGTH_SHORT).show();
//
//            //check if a new account needs to be created if their is no account with the given account details
//            assert acct != null;
//            String emailCheck = acct.getEmail();
//            String passCheck = "#default12345";
//
//            //if returned false, the user already has an account with the google credentials in our application
//
//            if(checkForUser(emailCheck, passCheck)) {
//                Toast.makeText(this, "New account created successfully", Toast.LENGTH_SHORT).show();
//            }
//
//            //changing the activity
//            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//            finish();
//        }
//    }
//
//    private boolean checkForUser(String email, String password) {
//        final boolean[] newUser = {false};
//        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                newUser[0] = true;
//            }
//        });
//        return newUser[0];
//    }

    private void loginUser() {
        //now check if the user enters correct results with the firebase database
        String email = email100.getText().toString().toLowerCase(Locale.ROOT).trim();
        String password = password100.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email, password).

                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, MainActivity2.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Log.d(TAG, "onConnectionFailed: " + connectionResult.getErrorMessage());
//    }
}