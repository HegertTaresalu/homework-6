package com.example.login.viewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;
import com.example.login.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

//custom adapter class for  recyclerView
public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.UserviewHolder>{

        ArrayList<User> userArrayList;

        @NonNull
        @Override
        public UserviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user,parent,false);
            return new UserviewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull UserviewHolder holder, int position) {
            User user = userArrayList.get(position);
            holder.firstName.setText(user.getFirstName());
            holder.lastName.setText(user.getLastName());
            holder.email.setText(user.getEmail());
        }

        public UserRecyclerAdapter() {
            this.userArrayList = new ArrayList<>();
        }

        @Override
        public int getItemCount() {
            return userArrayList.size();
        }

        public void updateUserList(final ArrayList<User> userArrayList) {
            this.userArrayList = userArrayList;
            notifyDataSetChanged();
        }

        static class UserviewHolder extends RecyclerView.ViewHolder {
            private final TextInputEditText firstName;
            private final TextInputEditText lastName;
            private final TextInputEditText email;

            public UserviewHolder(@NonNull View itemView) {
                super(itemView);
                firstName = itemView.findViewById(R.id.firstName_edit_text_user);
                lastName = itemView.findViewById(R.id.lastName_edit_text_user);
                email = itemView.findViewById(R.id.email_edit_text_user);
            }
        }
    }
