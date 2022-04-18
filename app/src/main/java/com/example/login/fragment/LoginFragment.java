package com.example.login.fragment;

import static com.example.login.activity.inputControl.isValidEmail;
import static com.example.login.activity.inputControl.isValidPassword;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.login.R;
import com.example.login.model.LoginRegisterModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public  class LoginFragment extends Fragment implements View.OnClickListener {

    private LoginRegisterModel loginRegisterModel;

    NavController navController;
    TextInputLayout emailEditText;
    TextInputLayout passwordEditText;

    public LoginFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginRegisterModel = new ViewModelProvider(this).get(LoginRegisterModel.class);
        loginRegisterModel.getUserMutableLiveData().observe(this, firebaseUser ->{
            if (firebaseUser != null){
                if(getView() != null) Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_userFragment);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Login Page");
       View view =  inflater.inflate(R.layout.fragment_login, container, false);
        //Button layout
        Button submitBtn = view.findViewById(R.id.submitBtn);
        submitBtn.setText("Login");

        //password Layout
        passwordEditText = view.findViewById(R.id.textInputLayoutPassword_);
        passwordEditText.setHelperTextEnabled(true);
        passwordEditText.setHelperText("Password: Qwerty123");



        //Email layout
        emailEditText = view.findViewById(R.id.textInputLayoutEmail_);
        emailEditText.setHelperTextEnabled(true);
        emailEditText.setHelperText("Email: example@email.com");



        //Register textview
        TextView register = view.findViewById(R.id.toRegistertxt);
        register.setTextSize(36);




        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.toRegistertxt).setOnClickListener(this);
        view.findViewById(R.id.submitBtn).setOnClickListener(this);
        view.findViewById(R.id.resetPasswordTxt).setOnClickListener(this);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onClick(View view) {
        emailEditText = getView().findViewById(R.id.textInputLayoutEmail_);
        passwordEditText = getView().findViewById(R.id.textInputLayoutPassword_);
        switch (view.getId()) {
            case R.id.toRegistertxt:
            navController.navigate(R.id.action_loginFragment_to_registerFragment);
            break;
            case R.id.submitBtn:
                String password = Objects.requireNonNull(passwordEditText.getEditText().getText().toString());
                String email = Objects.requireNonNull(emailEditText.getEditText()).getText().toString();
                if (isValidPassword(password) && isValidEmail(email)){
               loginRegisterModel.login(email,password);

                }
                break;
            case R.id.resetPasswordTxt:
                navController.navigate(R.id.action_loginFragment_to_resetPassword);
                break;


        }


    }
}