package com.example.login.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.login.viewModel.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoginRegisterModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<FirebaseUser> userMutableLiveData;

    public LoginRegisterModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        userMutableLiveData = authRepository.getUserMutableLiveData();
    }

    public void userRegistration(String firstName,String lastName, String email, String password){
        authRepository.userRegistration(firstName,lastName,email,password);

    }
    public void updateEmail(String email){
        authRepository.updateDb(email);

    }

    public void login(String email, String password){
        authRepository.logIn(email,password);
    }

    public void resetPassword(String email){
        authRepository.resetPassword(email);
    }

    public MutableLiveData getUserMutableLiveData(){

        return userMutableLiveData;
    }
}
