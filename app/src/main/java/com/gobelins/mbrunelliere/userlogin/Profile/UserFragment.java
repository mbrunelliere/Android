package com.gobelins.mbrunelliere.userlogin.Profile;

import android.app.Activity;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.gobelins.mbrunelliere.userlogin.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserFragment.userListener} interface
 * to handle interaction events.
 */
public class UserFragment extends Fragment {

    private userListener mListener;
    private Firebase myFirebaseRef;
    private Firebase myCurrentUserRef;

    private String emailString;

    public UserFragment() {
        // Required empty public constructor
    }

    @Bind(R.id.editName)
    TextView mProfileName;
    @Bind(R.id.editEmail)
    TextView mProfileMail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);

        myFirebaseRef = new Firebase("https://workshopandroid.firebaseio.com");
        AuthData authData = myFirebaseRef.getAuth();

        emailString = authData.getProviderData().get("email").toString();
        mProfileMail.setText(emailString);

        myCurrentUserRef = myFirebaseRef.child("users/" + authData.getUid());
        myCurrentUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mProfileName.setText(snapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });




        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (userListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.saveButton)
    void onClick(View v) {
        mListener.onSaveButtonClicked(myCurrentUserRef, mProfileName.getText().toString(), mProfileMail.getText().toString());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface userListener {
        void onSaveButtonClicked(Firebase userRef, String name, String email);
    }

}
