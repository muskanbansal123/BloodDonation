package com.example.keshav.projecttcs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by keshav on 21-06-2017.
 */

public class Signup extends AppCompatActivity {

    EditText username, email, password, confirm, gender, age, contact, city, pincode, bloodgroup;
    Button createAccount;
    TextView already;

    String memail, musername, mpassword, mconfirm, mgender, mage, mcontact, mcity, mpincode, mbloodgroup;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    DatabaseReference myRef;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //User successfully Signed In
                    Log.d("Firebase", "onAuthStateChanged:signed_in:" + user.getUid());

                    UserProfileChangeRequest up = new UserProfileChangeRequest.Builder()
                            .setDisplayName(username.getText().toString()).build();


                    user.sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Log.w("App", "EmailVerification");
                                    if(!task.isSuccessful()){
                                        Log.w("App", "EmailVerification", task.getException());
                                    }
                                }
                            });
                    user.updateProfile(up);
                    finish();
                } else {
                    //User Signed out
                    Log.d("Firebase", "onAuthStateChanged:signed_out");
                }
            }
        };



        username = (EditText) findViewById(R.id.input_name);
        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        confirm = (EditText) findViewById(R.id.input_confirmpassword);
        gender = (EditText) findViewById(R.id.input_gender);
        age = (EditText) findViewById(R.id.input_Age);
        contact = (EditText) findViewById(R.id.input_phone);
        city = (EditText) findViewById(R.id.input_City);
        pincode = (EditText) findViewById(R.id.input_Pincode);
        bloodgroup = (EditText) findViewById(R.id.input_bloodgroup);

        createAccount = (Button) findViewById(R.id.Bsignupbutton);
        already = (TextView) findViewById(R.id.link_login);

        mAuth.addAuthStateListener(mAuthListener);
    }

    public void sign_Up(View view) {
        memail = email.getText().toString();
        mpassword = password.getText().toString();
        musername = username.getText().toString();
        mconfirm = confirm.getText().toString();
        mgender = confirm.getText().toString();
        mage = age.getText().toString();
        mcontact = contact.getText().toString();
        mcity = city.getText().toString();
        mpincode = pincode.getText().toString();
        mbloodgroup = bloodgroup.getText().toString();

        if (memail.isEmpty() || mpassword.isEmpty() || musername.isEmpty() || mconfirm.isEmpty() || mgender.isEmpty() || mage.isEmpty() || mcontact.isEmpty() || mcity.isEmpty() || mpincode.isEmpty()){
            Snackbar.make(view, "Fill the details completely", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (!mpassword.equals(mconfirm)){
            password.setError("Passwords don't match");
            confirm.setError("Passwords don't match");
            return;
        }
        //showProgressDialog();

        mAuth.createUserWithEmailAndPassword(memail, mpassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Firebase", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("App", "signInWithEmail", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        //hideProgressDialog();
                        // ...
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthException) {
                            String error = ((FirebaseAuthException) e).getErrorCode();
                            Log.e("Error", error);


                        }
                    }
                });
        addtoFirebase();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(mAuthListener);
    }

    private void addtoFirebase(){
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            Intent intent = new Intent(this, loginn.class);
            startActivity(intent);
        }else {
            String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            myRef = FirebaseDatabase.getInstance().getReference();
            myRef.child("Donors").child(Uid).child("Name").setValue(musername);
            myRef.child("Donors").child(Uid).child("Gender").setValue(mgender);
            myRef.child("Donors").child(Uid).child("Age").setValue(mage);
            myRef.child("Donors").child(Uid).child("Blood Group").setValue(mbloodgroup);
            myRef.child("Donors").child(Uid).child("Contact").setValue(mcontact);
            myRef.child("Donors").child(Uid).child("City").setValue(mcity);
            myRef.child("Donors").child(Uid).child("PinCode").setValue(mpincode);
        }
    }
}

