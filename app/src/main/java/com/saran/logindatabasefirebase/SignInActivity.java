package com.saran.logindatabasefirebase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by core I5 on 4/7/2017.
 */

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.et_signin_email)
    EditText etEmail;

    @BindView(R.id.et_signin_password)
    EditText etPassword;

    @BindView(R.id.btn_login)
    Button btnLogin;

    //Declare firebase auth
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        ButterKnife.bind(this);

        //Initialize firebase auth
        mAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.btn_login)
    void login(){
        final String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if(!checkEmpty(email) && !checkEmpty(password)){
            //sign in with email and password
            mAuth.signInWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                preferences.edit().putString("email",email).commit();
                                Toast.makeText(SignInActivity.this,"Login successful!!!",Toast.LENGTH_SHORT).show();
                                Intent postLoginIntent = new Intent(SignInActivity.this,PostLoginActivity.class);
                                postLoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(postLoginIntent);
                                finish();
                            } else {
                                Toast.makeText(SignInActivity.this,"Cannot login!!!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(SignInActivity.this,"Every field is required",Toast.LENGTH_SHORT).show();
        }
    }

    boolean checkEmpty(String str){
        if(TextUtils.isEmpty(str.trim())){
            return true;
        }else {
            return false;
        }
    }
}
