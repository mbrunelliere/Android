package com.gobelins.mbrunelliere.userlogin;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.gobelins.mbrunelliere.userlogin.DrawerTabs.ReceivedFragment;
import com.gobelins.mbrunelliere.userlogin.DrawerTabs.SentFragment;
import com.gobelins.mbrunelliere.userlogin.DrawerTabs.TabFragment;
import com.squareup.picasso.Picasso;

public class MessengerActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    ImageButton mEditProfileButton;
    TextView mMailUser;
    TextView mNameUser;
    ImageView mImageUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        /**
         *Setup the DrawerLayout and NavigationView
         */

             mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
             mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

             mFragmentManager = getSupportFragmentManager();
             mFragmentTransaction = mFragmentManager.beginTransaction();
             mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();


        /**
         * Manage header on Drawer Navigation
         */

        mEditProfileButton = (ImageButton) findViewById(R.id.editProfileButton);
        mEditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfileEdition(v);
            }
        });

        /**
         * Setup click events on the Navigation View Items.
         */

             mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.nav_item_messenger) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
                }
                 if (menuItem.getItemId() == R.id.nav_item_sent) {
                     FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                     xfragmentTransaction.replace(R.id.containerView,new SentFragment()).commit();
                 }
                 if (menuItem.getItemId() == R.id.nav_item_received) {
                     FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                     xfragmentTransaction.replace(R.id.containerView,new ReceivedFragment()).commit();
                 }

                 if (menuItem.getItemId() == R.id.nav_item_logout) {
                     Firebase FirebaseRef = new Firebase("https://workshopandroid.firebaseio.com");
                     FirebaseRef.unauth();
                     Intent i = new Intent(MessengerActivity.this, MainActivity.class);
                     startActivity(i);
                 }
                 return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

                android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
                ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

                mDrawerLayout.setDrawerListener(mDrawerToggle);

                mDrawerToggle.syncState();

        /**
         *  Display current user information
         * */
        mImageUser = (ImageView) findViewById(R.id.userImage);
        mNameUser = (TextView) findViewById(R.id.nameUser) ;
        mMailUser = (TextView) findViewById(R.id.mailUser) ;

        Firebase myFirebaseRef = new Firebase("https://workshopandroid.firebaseio.com");
        AuthData authData = myFirebaseRef.getAuth();

        String imageString = authData.getProviderData().get("profileImageURL").toString();
        Picasso.with(this).load(imageString).into(mImageUser);

        String emailString = authData.getProviderData().get("email").toString();
        mMailUser.setText(emailString);

        Firebase myCurrentUserRef = myFirebaseRef.child("users/" + authData.getUid());
        myCurrentUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mNameUser.setText(snapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    private void goToProfileEdition(View v) {
        Intent userActivity = new Intent(MessengerActivity.this, UserActivity.class);
        startActivity(userActivity);
    }
}