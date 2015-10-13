package com.gobelins.mbrunelliere.userlogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.gobelins.mbrunelliere.userlogin.Profile.UserFragment;


public class UserActivity extends AppCompatActivity implements UserFragment.UserListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        int password = getIntent().getIntExtra("password", 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UserFragment userFragment = new UserFragment();

        //instantiate Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainContainer, new UserFragment())
                .commit();
    }


}
