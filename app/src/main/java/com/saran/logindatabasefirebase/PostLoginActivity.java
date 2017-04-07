package com.saran.logindatabasefirebase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by core I5 on 4/7/2017.
 */

public class PostLoginActivity extends AppCompatActivity {

    @BindView(R.id.tv_postlogin_name)
    TextView tvPostLoginName;

    @BindView(R.id.tv_postlogin_age)
    TextView tvPostLoginAge;

    @BindView(R.id.tv_postlogin_address)
    TextView tvPostLoginAddress;

    @BindView(R.id.tv_postlogin_email)
    TextView tvPostLoginEmail;

    String email;

    //Declare database reference
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postlogin);

        ButterKnife.bind(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        email = preferences.getString("email","");

        //Instantiate database reference
        mRef = FirebaseDatabase.getInstance().getReference();

        initViews();
    }

    private void initViews() {
        if(email != null && !email.isEmpty()){
            if(mRef!=null){
                //Query all data of child Users
                Query query = mRef.child("Users");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = null;
                        if(dataSnapshot.exists()){
                            for (DataSnapshot result : dataSnapshot.getChildren()){
                                user = result.getValue(User.class);
                                if(user.getEmail().equals(email)){
                                    break;
                                }
                            }
                            tvPostLoginName.setText(user.getName());
                            tvPostLoginAge.setText(user.getAge());
                            tvPostLoginAddress.setText(user.getAddress());
                            tvPostLoginEmail.setText(user.getEmail());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

}
