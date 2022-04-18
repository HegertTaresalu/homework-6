package com.example.login.viewModel;


import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.login.R;
import com.example.login.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//repository class for db + auth
public class AuthRepository {
    public static final String TAG = "Firebase";

    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore db =  FirebaseFirestore.getInstance();

    private final MutableLiveData<FirebaseUser> userMutableLiveData;
    private final MutableLiveData<Boolean> loggedOutMutableLiveData;
    private final MutableLiveData<ArrayList<User>> userLiveData;
    private final ArrayList<User> userArrayList = new ArrayList<>();
    private final Application application;


    public AuthRepository(Application application) {
        this.application = application;
        firebaseAuth = FirebaseAuth.getInstance();
        userMutableLiveData = new MutableLiveData<>();
        loggedOutMutableLiveData = new MutableLiveData<>();
        userLiveData = new MutableLiveData<>();

        if (firebaseAuth.getCurrentUser() != null){
            userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutMutableLiveData.postValue(false);
            loadUserData();
        }
    }




    //method for registering email
    public void userRegistration(String firstName, String lastName, String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                   if (task.isSuccessful()){
                       String userId = firebaseAuth.getCurrentUser().getUid();
                       //Creates new collection named users if one doesn't exist into it add a new document UID reference
                       DocumentReference documentReference = db.collection("users").document(userId);
                       Map<String,Object> user = new HashMap<>();
                       user.put("firstName",firstName);
                       user.put("lastName",lastName);
                       user.put("email",email);
                       documentReference.set(user).addOnSuccessListener(aVoid -> Log.i(TAG, "onSuccess:user data was saved"))
                       .addOnFailureListener(e-> Log.e(TAG,"onFailure: error writing to db"));
                       userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                   }
                   else{
                       Toast.makeText(application, application.getString(R.string.error, task.getException().getMessage()),Toast.LENGTH_SHORT).show();
                   }

                });

    }

    public void updateDb(String email){
        DocumentReference nameRef = db.collection("users").document(email);
        nameRef
                .update("email", true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });

    }



    public void logOut(){
        firebaseAuth.signOut();
        loggedOutMutableLiveData.postValue(true);

    }

    public void logIn(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        userMutableLiveData.postValue(firebaseAuth.getCurrentUser());

                    }
                    else{
                        Toast.makeText(application, application.getString(R.string.error, task.getException().getMessage()), Toast.LENGTH_SHORT).show();
                    }
                });



    }



    public void loadUserData(){
        if (firebaseAuth.getCurrentUser() != null){
            String uid = firebaseAuth.getCurrentUser().getUid();
            DocumentReference doc = db.collection("users").document(uid);
            doc.get()
                    .addOnSuccessListener(documentSnapshot -> {
                        User user = documentSnapshot.toObject(User.class);
                        userArrayList.add(user);
                       userLiveData.setValue(userArrayList);
                    }).addOnFailureListener(e ->
                            Toast.makeText(application, application.getString(R.string.error, e.getMessage()), Toast.LENGTH_SHORT).show()
                    );

        }

    }

    public void resetPassword(String email){
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Email sent.");
                    }
                });

    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() {
        return loggedOutMutableLiveData;
    }

    public MutableLiveData<ArrayList<User>> getUserLiveData() {
        return userLiveData;
    }
}
