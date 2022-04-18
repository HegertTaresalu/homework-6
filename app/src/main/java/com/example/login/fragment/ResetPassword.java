package com.example.login.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.login.R;
import com.example.login.model.LoginRegisterModel;
import com.google.android.material.snackbar.Snackbar;

public class ResetPassword extends Fragment implements View.OnClickListener {

    private LoginRegisterModel loginRegisterModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginRegisterModel = new ViewModelProvider(this).get(LoginRegisterModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_reset_password, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.submitResetBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText emailEditText = getView().findViewById(R.id.editTextPersonEmail_);
        String email = emailEditText.getText().toString();
        loginRegisterModel.resetPassword(email);
    }
}