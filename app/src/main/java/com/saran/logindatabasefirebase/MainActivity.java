package com.saran.logindatabasefirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_signup)
    Button btnSignUp;

    @BindView(R.id.btn_signin)
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_signup)
    void signUp(){
        Intent signUpIntent = new Intent(MainActivity.this,SignUpActivity.class);
        startActivity(signUpIntent);
    }

    @OnClick(R.id.btn_signin)
    void signIn(){
        Intent signInIntent = new Intent(MainActivity.this,SignInActivity.class);
        startActivity(signInIntent);
    }
}
