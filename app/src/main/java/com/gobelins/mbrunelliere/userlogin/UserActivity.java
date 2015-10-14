package com.gobelins.mbrunelliere.userlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.firebase.client.Firebase;
import com.gobelins.mbrunelliere.userlogin.Profile.UserFragment;


public class UserActivity extends AppCompatActivity implements UserFragment.userListener {

    private Firebase myFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://workshopandroid.firebaseio.com");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UserFragment userFragment = new UserFragment();

        setTitle("Profil utilisateur");

        //instantiate Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainContainer, new UserFragment())
                .commit();
    }


    @Override
    public void onLogoutClicked() {
        myFirebaseRef.unauth();
        Intent i = new Intent(UserActivity.this, MainActivity.class);
        startActivity(i);
    }
}
