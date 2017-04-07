package com.saran.logindatabasefirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by core I5 on 4/7/2017.
 */

public class SignUpActivity extends AppCompatActivity{

    @BindView(R.id.et_name)
    EditText etName;

    @BindView(R.id.et_age)
    EditText etAge;

    @BindView(R.id.et_address)
    EditText etAddress;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.btn_register)
    Button btnRegister;

    //Declare auth
    private FirebaseAuth mAuth;

    //Declare database reference
    private DatabaseReference mRef;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        //Initialize auth
        mAuth = FirebaseAuth.getInstance();

        //Initialize database reference
        mRef = FirebaseDatabase.getInstance().getReference();
    }

    @OnClick(R.id.btn_register)
    void registerUser(){
        final String name = etName.getText().toString();
        final String age = etAge.getText().toString();
        final String address = etAddress.getText().toString();
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        if(!checkEmpty(name) && !checkEmpty(age) && !checkEmpty(address) && !checkEmpty(email) && !checkEmpty(password)){
            if(isValidEmail(email)){
                //Create user with email and password
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(name,age,email,address,password);
                            // Enter user details in database. Push() generates unique node ID every time.
                            mRef.child("Users").push().setValue(user, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if(databaseError != null){
                                        Toast.makeText(SignUpActivity.this,"Database error!!!",Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignUpActivity.this,"User successfully created!!!",Toast.LENGTH_SHORT).show();
                                        Intent intentMainActivity = new Intent(SignUpActivity.this,MainActivity.class);
                                        intentMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intentMainActivity);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(SignUpActivity.this,"User creation unsuccessful!!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(SignUpActivity.this,"Not a valid email",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SignUpActivity.this,"Every field is required",Toast.LENGTH_SHORT).show();
        }
    }

    boolean isValidEmail(String email){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    boolean checkEmpty(String str){
        if(TextUtils.isEmpty(str.trim())){
            return true;
        }else {
            return false;
        }
    }
}
