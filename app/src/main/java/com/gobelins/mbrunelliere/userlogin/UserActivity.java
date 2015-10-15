package com.gobelins.mbrunelliere.userlogin;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.gobelins.mbrunelliere.userlogin.Profile.UserFragment;

import java.util.HashMap;
import java.util.Map;


public class UserActivity extends AppCompatActivity implements UserFragment.userListener {

    private static final String TAG = "UserActivity";
    private Firebase myFirebaseRef;

    public String passwordSession;
    public String emailSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        passwordSession = getIntent().getStringExtra("passwordSession");
        emailSession = getIntent().getStringExtra("emailSession");

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
    public void onSaveButtonClicked(Firebase userRef, String name, String newEmail) {
        Map<String, Object> user = new HashMap<String, Object>();
        user.put("name", name);
        user.put("email", newEmail);
        userRef.updateChildren(user);

        Log.d(TAG, "email :" + emailSession + "et password :" + passwordSession);

        myFirebaseRef.changeEmail("marion@gmail.com", newEmail, "marion", new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Snackbar.make(findViewById(R.id.mainWrapper), "Vos modifications ont été prises en compte", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            public void onClick(View v) {
                                Log.d(TAG, "OK");
                            }
                        })
                        .show();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Log.d(TAG, "Firebase bug");
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings) {
            //login clicked
            return true;
        }else if (item.getItemId() == R.id.actions_Profile) {
            //profile clicked
            Intent userActivity = new Intent(UserActivity.this, UserActivity.class);
            startActivity(userActivity);
            return true;
        }else if (item.getItemId() == R.id.actions_Logout) {
            //logout clicked

            Firebase FirebaseRef = new Firebase("https://workshopandroid.firebaseio.com");
            FirebaseRef.unauth();
            Intent i = new Intent(UserActivity.this, MainActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }
}
