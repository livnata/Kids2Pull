package com.kids2pull.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kids2pull.android.R;
import com.kids2pull.android.models.User;
import com.kids2pull.android.models.UserType;

import org.joda.time.DateTime;

public class SignInActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";

    private DatabaseReference mUsersDatabaseRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ChildEventListener mChildEventListener;

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mSignInButton;
    private Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        FirebaseApp.initializeApp(getBaseContext());
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        //x.getApplicationContext();
//        mAuth = FirebaseAuth.getInstance();

        mUsersDatabaseRef = mFirebaseDatabase.getReference().child("Users");

        // Views
        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);
        mSignInButton = (Button) findViewById(R.id.button_sign_in);
        mSignUpButton = (Button) findViewById(R.id.button_sign_up);

        // Click listeners
        mSignInButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);



    }

    @Override
    public void onStart() {
        super.onStart();

//            mAuth.addAuthStateListener(mAuthListener);
//        // Check auth on Activity start
//        if (mAuth.getCurrentUser() != null) {
//            onAuthSuccess(mAuth.getCurrentUser());
//        }
    }

    @Override
    public void onStop(){
        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
    }

    private void signIn() {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();


//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
//                        hideProgressDialog();
//
//                        if (task.isSuccessful()) {
//                            onAuthSuccess(task.getResult().getUser());
//                        } else {
//                            Toast.makeText(SignInActivity.this, "Sign In Failed",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
    }

    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        // Write new User
        writeNewUser(password, "lio",email);

        hideProgressDialog();

//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
//                        hideProgressDialog();
//
//                        if (task.isSuccessful()) {
//                            onAuthSuccess(task.getResult().getUser());
//                        } else {
//                            Toast.makeText(SignInActivity.this, "Sign Up Failed",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
    }

    private void onAuthSuccess(User user) {
        String username = usernameFromEmail(user.getEmail());

        // Write new User
        writeNewUser(user.getPhone_number1(), username, user.getEmail());

        // Go to MainActivity
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            mEmailField.setError("Required");
            result = false;
        } else {
            mEmailField.setError(null);
        }

        if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        return result;
    }

    // [START basic_write]
    private void writeNewUser(String userId, String name, String email) {
        String username = usernameFromEmail(email);

        // Write new User


        User user = new User("lior","ezra","liorez@gmail.com", DateTime.now(), UserType.PARENT, "05211111111");
        mUsersDatabaseRef.push().setValue(user);

        mChildEventListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();
                System.out.println("Data saved successfully.");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };
        mUsersDatabaseRef.addChildEventListener(mChildEventListener);



    }

    // [END basic_write]

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_sign_in) {
            signIn();
        } else if (i == R.id.button_sign_up) {
            signUp();
        }
    }
}
