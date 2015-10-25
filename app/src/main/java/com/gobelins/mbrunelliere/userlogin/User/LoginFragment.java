package com.gobelins.mbrunelliere.userlogin.User;

import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gobelins.mbrunelliere.userlogin.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private static final String TAG  = "LoginFrament";
    private LoginListener mListener;


    @Bind(R.id.loginMail)
    TextView mLoginMail;
    @Bind(R.id.loginPassword)
    TextView mLoginPassword;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //context -> activity
        try {
            mListener = (LoginListener) context;
        } catch (ClassCastException exception) {
            throw new ClassCastException(context.toString()
                    + " must implement LoginFragment.LoginListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView(){
        ButterKnife.unbind(this);
        mListener = null;

        super.onDestroyView();
    }

    @OnClick(R.id.loginButton)
    void onClick(View v) {
        Log.d(TAG, "onClickListener");
        mListener.onLoginClicked(mLoginMail.getText(), mLoginPassword.getText());
    }

    @OnClick(R.id.registerUserButton)
    void onClick() {
        mListener.onRegisterActivityClicked();
    }

    public interface LoginListener {
        void onLoginClicked(CharSequence login, CharSequence password);
        void onRegisterActivityClicked();

    }



}
