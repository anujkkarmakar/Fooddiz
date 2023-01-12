package com.example.fooddiz;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {

    private AppCompatEditText name100, email100, password100;
    private AppCompatButton button100;
    private AppCompatTextView google, loginPage;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private GoogleSignInOptions gso;
    private GoogleApiClient googleApiClient;
    private final static int RC_SIGN_IN = 2;
    ProgressDialog progressDialog;
    private final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    String imageURI;
    CircleImageView profileImage;
    Uri imageUri;

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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);


//        oneTapClient = Identity.getSignInClient(this);
//        signInRequest = BeginSignInRequest.builder()
//                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
//                        .setSupported(true)
//                        .build())
//                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                        .setSupported(true)
//                        // Your server's client ID, not your Android client ID.
//                        .setServerClientId(getString(R.string.default_web_client_id))
//                        // Only show accounts previously used to sign in.
//                        .setFilterByAuthorizedAccounts(true)
//                        .build())
//                // Automatically sign in when exactly one credential is retrieved.
//                .setAutoSelectEnabled(true)
//                .build();

//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//
//
//        google.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                googleLogIn();
//            }
//        });

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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case REQ_ONE_TAP:
//                try {
//                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
//                    String idToken = credential.getGoogleIdToken();
//                    if (idToken !=  null) {
//                        // Got an ID token from Google. Use it to authenticate
//                        // with Firebase.
//                        Log.d(TAG, "Got ID token.");
//                        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
//                        mAuth.signInWithCredential(firebaseCredential)
//                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                        if (task.isSuccessful()) {
//                                            // Sign in success, update UI with the signed-in user's information
//                                            Log.d(TAG, "signUpWithCredential:success");
//                                            FirebaseUser user = mAuth.getCurrentUser();
//                                            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
//                                            finish();
//                                        } else {
//                                            // If sign in fails, display a message to the user.
//                                            Log.w(TAG, "signUpWithCredential:failure", task.getException());
//                                            //updateUI(null);
//                                        }
//                                    }
//                                });
//                    }
//                } catch (ApiException e) {
//                    switch (e.getStatusCode()) {
//                        case CommonStatusCodes.CANCELED:
//                            Log.d(TAG, "One-tap dialog was closed.");
//                            // Don't re-prompt the user.
//                            showOneTapUI = false;
//                            break;
//                        case CommonStatusCodes.NETWORK_ERROR:
//                            Log.d(TAG, "One-tap encountered a network error.");
//                            // Try again or just ignore.
//                            break;
//                        default:
//                            Log.d(TAG, "Couldn't get credential from result."
//                                    + e.getLocalizedMessage());
//                            break;
//                    }
//                }
//                break;
//        }
//    }

    /**
     * Using Firebase default sign in options
     */

    private void signUpUser() {
        String name = name100.getText().toString().trim();
        String email = email100.getText().toString().trim().toLowerCase(Locale.ROOT);
        String password = password100.getText().toString().trim().toLowerCase(Locale.ROOT);

        progressDialog.show();

//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()) {
//                            //get the current user and do the following
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            Log.d(TAG, "createUserWithEmail:success");
//                            progressDialog.dismiss();
//                            //updateUI
//                            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
//                            finish();
//                        }
//                        else {
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            progressDialog.dismiss();
        } else if (!email.matches(emailPattern)) {
            progressDialog.dismiss();
            email100.setError("Enter valid email");
        } else if (password.length() < 6) {
            progressDialog.dismiss();
            password100.setError("Password length must be at-least 6 characters");
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                                //TODO: TO BE UPDATED
                                assert mAuth != null;
                                DatabaseReference reference = database.getReference().child("users").child(mAuth.getUid());
                                StorageReference storageReference = storage.getReference().child("upload").child(mAuth.getUid());

                                //TODO:
                                //The facility will be provided to the user to register along with the uploading of the picture
                                if (imageUri != null) {
                                    storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                            @Override
                                                            public void onSuccess(Uri uri) {
                                                                imageURI = uri.toString();
                                                                Users user = new Users(name, email, imageURI, mAuth.getUid());
                                                                reference.setValue(user)
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    progressDialog.dismiss();
                                                                                    Toast.makeText(SignUpActivity.this, "Successfully created", Toast.LENGTH_SHORT).show();
                                                                                    startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                                                                                } else {
                                                                                    progressDialog.dismiss();
                                                                                    Toast.makeText(SignUpActivity.this, "Error created profile", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }
                                                                        });
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(SignUpActivity.this, "Something went wrong. Try again.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        }
                                    });
                                } else {
                                    imageURI = "https://firebasestorage.googleapis.com/v0/b/fooddiz-d948d.appspot.com/o/profile_image.png?alt=media&token=71b6a4c9-6711-477a-96b0-f5b8b004b8d8";
                                    Users user = new Users(name, email, imageURI, mAuth.getUid());
                                    reference.setValue(user)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(SignUpActivity.this, "Successfully created", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                                                    } else {
                                                        Toast.makeText(SignUpActivity.this, "Error created profile", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            } else {
                                progressDialog.dismiss();
                                // If sign in fails, display a message to the user.
                                Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

//    private void googleLogIn() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
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
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Log.d(TAG, "onConnectionFailed: " + connectionResult.getErrorMessage());
//    }
}
