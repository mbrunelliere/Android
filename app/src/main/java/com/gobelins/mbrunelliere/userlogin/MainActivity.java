package com.gobelins.mbrunelliere.userlogin;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.gobelins.mbrunelliere.userlogin.Profile.User;
import com.gobelins.mbrunelliere.userlogin.User.LoginFragment;
import com.gobelins.mbrunelliere.userlogin.User.RegisterFragment;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener, RegisterFragment.RegisterListener {

    private static final String TAG = "MainActivity";
    private Firebase myFirebaseRef;

    private String passwordSession;
    private String emailSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://workshopandroid.firebaseio.com");
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LoginFragment loginFragment = new LoginFragment();
        RegisterFragment registerFragment = new RegisterFragment();

        setTitle("Bienvenue");

        //instantiate Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainContainer, new LoginFragment())
                .commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuMainRegisterItem) {
            //register clicked
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, new RegisterFragment())
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onLoginClicked(CharSequence loginText, CharSequence passwordText) {
        Log.d(TAG, "onLoginClicked in LoginActivity ");
        passwordSession = passwordText.toString();
        emailSession = loginText.toString();

        myFirebaseRef.authWithPassword(loginText.toString(), passwordText.toString(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                Snackbar.make(findViewById(R.id.mainWrapper), "Vous êtes connecté", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            public void onClick(View v) {
                                Log.d(TAG, "ok");
                            }
                        })
                        .show();
                Intent chatActivity = new Intent(MainActivity.this, ChatActivity.class);
                chatActivity.putExtra("emailSession",emailSession);
                chatActivity.putExtra("passwordSession",passwordSession);
                startActivity(chatActivity);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error
            }
        });

    }

    @Override
    public void onRegisterClicked(User user) {
        Log.d(TAG, "onLoginClicked in RegisterActivity ");
        final User currentUser = user;

        //Add User with Firebase Auth
        myFirebaseRef.createUser(user.getEmail(), user.getPassword(), new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                System.out.println("Successfully created user account with uid: " + result.get("uid"));
                Snackbar.make(findViewById(R.id.mainWrapper), "Vous êtes inscrit", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            public void onClick(View v) {
                                Log.d(TAG, "OK");
                            }
                        })
                        .show();
                //Add User in Firebase Data
                Firebase usersRef = myFirebaseRef.child("users/" + result.get("uid"));
                usersRef.setValue(currentUser);
                //Change fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainContainer, new LoginFragment())
                        .commit();

            }

            @Override
            public void onError(FirebaseError firebaseError) {
                // there was an error
            }
        });

    }


}
