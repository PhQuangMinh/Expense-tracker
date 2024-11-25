package org.example.projectassignment.controller.firebase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import org.example.projectassignment.model.user.informationuser.User;


public class FirebaseUser {
    public void initFirebase() throws IOException {
        if(FirebaseApp.getApps().isEmpty()) {
            String linkUrl = "https://sothuchi-60672-default-rtdb.firebaseio.com/";
            String serviceAccountFile = "sothuchi-60672-firebase-adminsdk-6kazn-c16a300118.json";
            FileInputStream serviceAccount = new FileInputStream(serviceAccountFile);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(linkUrl)
                    .build();
            FirebaseApp.initializeApp(options);
        }

    }


    public CompletableFuture<List<User>> getUsers(){
        CompletableFuture<List<User>> future = new CompletableFuture<>();
        try {
            initFirebase();
            DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("users");
            dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<User> users = new ArrayList<>();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        users.add(data.getValue(User.class));
                    }
                    future.complete(users);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    future.completeExceptionally(databaseError.toException());
                }
            });
        } catch (IOException e) {
            future.completeExceptionally(e);
        }
        return future;
    }

    public void saveUser(List<User> listUsers){
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("users");
        for (User user : listUsers) {
            dataRef.child(user.getId()).setValueAsync(user);
        }
    }

    public void updateUser(User user){
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("users");
        dataRef.child(user.getId()).setValueAsync(user);
    }
}
