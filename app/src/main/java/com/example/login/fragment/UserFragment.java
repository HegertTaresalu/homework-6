package com.example.login.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.login.R;
import com.example.login.model.UserViewModel;
import com.example.login.viewModel.UserRecyclerAdapter;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class UserFragment extends Fragment {

    public UserFragment() {
    }

    private UserViewModel userViewModel;
    private UserRecyclerAdapter userRecylerAdapter;

    TextInputLayout emailEditText;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("User Page");

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        setHasOptionsMenu(true);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        userRecylerAdapter = new UserRecyclerAdapter();
        recyclerView.setAdapter(userRecylerAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUserLiveData().observe(this, userArrayList ->  userRecylerAdapter.updateUserList(userArrayList));
        userViewModel.getLoggedOutMutableLiveData().observe(this, loggedOut -> {
            if (loggedOut) {
                if (getView()!=null) Navigation.findNavController(getView())
                        .navigate(R.id.action_userFragment_to_loginFragment);
            }
        });


    }




    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.close_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        navController = Navigation.findNavController(getView());
        userViewModel.logOut();

        return super.onOptionsItemSelected(item);

    }


}