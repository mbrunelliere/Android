package com.gobelins.mbrunelliere.userlogin.User;


import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.gobelins.mbrunelliere.userlogin.Profile.User;
import com.gobelins.mbrunelliere.userlogin.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private static final String TAG  = "RegisterFragment";
    private RegisterListener registerListener;

    @Bind(R.id.signinName)
    TextView mRegisterName;
    @Bind(R.id.signinMail)
    TextView mRegisterEmail;
    @Bind(R.id.signinPassword)
    TextView mRegisterPassword;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //context -> activity
        try {
            registerListener = (RegisterListener) context;
        } catch (ClassCastException exception) {
            throw new ClassCastException(context.toString()
                    + " must implement registrFragment.LoginListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.registerButton)
    void onClick(View v) {
        Log.d(TAG, "onClickListener");
        User user = new User(mRegisterName.getText().toString(), mRegisterEmail.getText().toString(), mRegisterPassword.getText().toString());

        registerListener.onRegisterClicked(user);
    }

    public interface RegisterListener {
        void onRegisterClicked(User user);
    }



}
