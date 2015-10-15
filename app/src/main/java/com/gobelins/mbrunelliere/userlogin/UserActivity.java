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
import android.widget.Toast;

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

        myFirebaseRef.changeEmail("user2@gmail.com", "user2.1@gmail.com", "user2", new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Toast.makeText(UserActivity.this, "Modifications sauvegardées", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Email changé");
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                // error encountered
                switch (firebaseError.getCode()) {
                    case FirebaseError.USER_DOES_NOT_EXIST:
                        Toast.makeText(UserActivity.this, "unknown user", Toast.LENGTH_LONG).show();
                        break;
                    case FirebaseError.INVALID_EMAIL:
                        Toast.makeText(UserActivity.this, "invalid email", Toast.LENGTH_LONG).show();
                        break;
                    case FirebaseError.INVALID_PASSWORD:
                        Toast.makeText(UserActivity.this, "invalid password", Toast.LENGTH_LONG).show();
                        break;
                    case FirebaseError.DENIED_BY_USER:
                        Toast.makeText(UserActivity.this, "USER DENIED", Toast.LENGTH_LONG).show();
                        break;
                    case FirebaseError.UNKNOWN_ERROR:
                        Toast.makeText(UserActivity.this, "UNKNOWN ERROR", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(UserActivity.this, "an error occured", Toast.LENGTH_LONG).show();
                        break;
                }
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
