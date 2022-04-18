package com.example.login.fragment;

import static com.example.login.activity.inputControl.isValidEmail;
import static com.example.login.activity.inputControl.isValidPassword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.login.R;
import com.example.login.model.LoginRegisterModel;
import com.example.login.model.UserViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


public class RegisterFragment extends Fragment {

    private LoginRegisterModel loginRegisterModel;
    private UserViewModel userViewModel;

    public RegisterFragment(){

        }

    NavController navController;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginRegisterModel = new ViewModelProvider(this).get(LoginRegisterModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUserMutableLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null){
                Toast.makeText(getContext(),"User logged in", Toast.LENGTH_LONG).show();
            }
        });

       userViewModel.getLoggedOutMutableLiveData().observe(this,loggedOut ->{
            if (loggedOut){
                if(getView()!= null) Navigation.findNavController(getView()).navigate(R.id.action_registerFragment_to_userFragment);
            }

       });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Register Page");
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.save_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        TextInputLayout firstNameEditText = getView().findViewById(R.id.textInputLayoutFirstName);
        TextInputLayout lastNameEditText = getView().findViewById(R.id.textInputLayoutLastName);
        TextInputLayout emailEditText = getView().findViewById(R.id.textInputLayoutEmail);
        TextInputLayout passwordEditText = getView().findViewById(R.id.textInputLayoutPassword);
        TextInputLayout passwordConfirmEditText = getView().findViewById(R.id.textInputLayoutConfirmPassword);

        String firstName = firstNameEditText.getEditText().getText().toString();
        String lastName = lastNameEditText.getEditText().getText().toString();
        String email = emailEditText.getEditText().getText().toString().toLowerCase();
        String password = passwordEditText.getEditText().getText().toString();
        String passwordConfirm = passwordConfirmEditText.getEditText().getText().toString();

        //kontrollib kõiki sisendeid
        if (isValidEmail(email) && isValidPassword(password) && password.equals(passwordConfirm)  && !firstName.isEmpty() && !lastName.isEmpty()){

            loginRegisterModel.userRegistration(firstName,lastName,email,password);
            userViewModel.logOut();
        }
        else  {
            if (firstName.isEmpty()){
                Snackbar.make(getView(), "First name field is empty",Snackbar.LENGTH_SHORT).show();

            }
            else if (lastName.isEmpty()){
                Snackbar.make(getView(), "Last name field is empty",Snackbar.LENGTH_SHORT).show();


            }
            else if (!isValidEmail(email)){
                Snackbar.make(getView(),"Email is not valid", Snackbar.LENGTH_SHORT).show();
            }
            else if (!isValidPassword(password)){
                Snackbar.make(getView(),"Password is not valid",Snackbar.LENGTH_SHORT).show();
            }
            else if (!password.equals(passwordConfirm) ){
                Snackbar.make(getView(),password.toString()+passwordConfirm.toString(),Snackbar.LENGTH_SHORT).show();

            }
            else{
                Snackbar.make(getView(),"Multible inputs are not valid",Snackbar.LENGTH_SHORT).show();
            }
            navController.navigate(R.id.loginFragment);


        }



        return super.onOptionsItemSelected(item);
    }
}
