package com.kids2pull.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kids2pull.android.R;
import com.kids2pull.android.models.Event;
import com.kids2pull.android.models.Hobby;
import com.kids2pull.android.models.HobbyType;
import com.kids2pull.android.models.User;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;

public class SignInActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";

    private DatabaseReference mUsersDatabaseRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ChildEventListener mChildEventListener;
    private FirebaseDatabase database;

    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mPhoneNumberField;
    private Button mSignInButton;
    private Button mSignUpButton;
    //DB
    private String userDisplayedName;
    private String userEmail;

    private User mUser;

    private ArrayList<User> mArrayListUsers;
    private ArrayList<String> mArrayUserIds;
    private ArrayList<Hobby> mArrayHobbies;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArrayListUsers = new ArrayList<User>();
        mArrayUserIds = new ArrayList<String>();
        mArrayHobbies = new ArrayList<Hobby>();


        setContentView(R.layout.activity_sign_in);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        //x.getApplicationContext();
        FirebaseApp.initializeApp(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    onAuthSuccess(user.getEmail());

                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        mUsersDatabaseRef = mFirebaseDatabase.getReference().child("Users");

        // Views
        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);
        mPhoneNumberField = (EditText) findViewById(R.id.field_phone_number);
        mSignInButton = (Button) findViewById(R.id.button_sign_in);
        mSignUpButton = (Button) findViewById(R.id.button_sign_up);

        // Click listeners
        mSignInButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);

        mSignUpButton.setOnLongClickListener( OnLongSignUp);


    }

    @Override
    public void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                hideProgressDialog();
                Toast.makeText(SignInActivity.this, R.string.sign_in_succeed, Toast.LENGTH_SHORT).show();
                if (!task.isSuccessful()) {
                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                    Toast.makeText(SignInActivity.this, R.string.sign_in_failed, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void signUp(final String aEmail, final String aPassword, final String aPhoneNumber, final boolean isLast) {
        Log.d(TAG, "signUp" + aEmail);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        mAuth.createUserWithEmailAndPassword(aEmail, aPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        hideProgressDialog();
                        Toast.makeText(SignInActivity.this, R.string.sign_up_succeed, Toast.LENGTH_SHORT).show();

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignInActivity.this, R.string.sign_up_failed, Toast.LENGTH_SHORT).show();
                        } else {
                            writeNewUser( aEmail, aPhoneNumber, aEmail, isLast);
                        }

                    }
                });
        hideProgressDialog();
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

    private void onAuthSuccess(String email) {
        userEmail = email;

    }

    // [START basic_write]
    // [START basic_write]
    private void writeNewUser(String userEmail, String phoneNumber, String userName, boolean isLast) {
        // Write new User
        mUser = new User(userName, userEmail, phoneNumber, "");
        database = FirebaseDatabase.getInstance();
        DatabaseReference mUsersDatabaseRef = database.getReference("users");
        DatabaseReference mUserRef = mUsersDatabaseRef.child(mUser.getUserId());
        mUserRef.setValue(mUser);

        mArrayListUsers.add( mUser);
        mArrayUserIds.add(mUser.getUserId());

        if (isLast){
           createDummyData2();
        }

        mChildEventListener = new ChildEventListener() {
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
//        mUsersDatabaseRef.addChildEventListener(mChildEventListener);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_sign_in) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.button_sign_up) {
            signUp(mEmailField.getText().toString(), mPasswordField.getText().toString(), mPhoneNumberField.getText().toString(), false);
        }
    }

    private View.OnLongClickListener OnLongSignUp = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            createDummyData();

            return true;
        }
    };


    private void writeNewHobby(Hobby aHobby, boolean isLast) {
        // Write new User
        database = FirebaseDatabase.getInstance();
        DatabaseReference mHobbiesDatabaseRef = database.getReference("hobbies");
        DatabaseReference mHobbiesRef = mHobbiesDatabaseRef.child(aHobby.getHobby_id());
        mHobbiesRef.setValue(aHobby);

        if (isLast){
          createDummyData3();
        }

    }


    private void writeNewEvent(Event aEvent, boolean isLast) {
        // Write new User
        database = FirebaseDatabase.getInstance();
        DatabaseReference mEventsDatabaseRef = database.getReference("events");
        DatabaseReference mEventsRef = mEventsDatabaseRef.child(aEvent.getEvent_id());
        mEventsRef.setValue(aEvent);

//        if (isLast){
//            createDummyData4();
//        }

    }



    private void createDummyData(){
//        TODO: clean database
//        database = FirebaseDatabase.getInstance();
//
//        DatabaseReference mHobbiesDatabaseRef = database.getReference("");
//        mHobbiesDatabaseRef.pu
//


        signUp("danysz@gmail.com", "dan1el!", "+972547755955", false);

        signUp("danysz@yahoo.com", "dan1el!@", "+972547755956", false);

        signUp("anna@yahoo.com", "dan1el!@", "+972547755956", false);

        signUp("danysz@yahoo1.com", "dan1el!@", "+972547755956", true);


    }

    private void createDummyData2(){
        Hobby currentHobby;

        currentHobby = new Hobby("Ballet", HobbyType.BALLET, "Tzur Itzhak", null, mArrayUserIds,new DateTime().toDate().getTime());

        mArrayHobbies.add( currentHobby);

        writeNewHobby( currentHobby, false);

        currentHobby = new Hobby("Ballet 2", HobbyType.BALLET, "Tzur Igal", null, mArrayUserIds,new DateTime().toDate().getTime());

        mArrayHobbies.add( currentHobby);

        writeNewHobby( currentHobby, false);

        currentHobby = new Hobby("Basketball", HobbyType.SPORT, "Tzur Itzhak", null, mArrayUserIds,new DateTime().plusDays(1 ).toDate().getTime());

        mArrayHobbies.add( currentHobby);

        writeNewHobby( currentHobby, false);

        currentHobby = new Hobby("Karate", HobbyType.MARTIAL, "Tzur Igal", null, mArrayUserIds,new DateTime().plusDays( 1).toDate().getTime());

        mArrayHobbies.add( currentHobby);

        writeNewHobby( currentHobby, true);

    }
    private void createDummyData3(){
        Event currentEvent;

        currentEvent = new Event( mArrayHobbies.get( 0).getHobby_id(), mArrayHobbies.get( 0).getHobby_date());

        writeNewEvent( currentEvent, false);

        currentEvent = new Event( mArrayHobbies.get( 1).getHobby_id(), mArrayHobbies.get( 1).getHobby_date());

        writeNewEvent( currentEvent, false);

        currentEvent = new Event( mArrayHobbies.get( 2).getHobby_id(), mArrayHobbies.get( 2).getHobby_date());

        writeNewEvent( currentEvent, false);
    }


}
