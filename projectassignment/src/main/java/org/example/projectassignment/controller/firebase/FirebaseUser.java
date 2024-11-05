package org.example.projectassignment.controller.firebase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import org.example.projectassignment.model.User;


public class FirebaseUser {
    public void initFirebase() throws IOException {
        String linkUrl = "https://sothuchi-60672-default-rtdb.firebaseio.com/";
        String serviceAccountFile = "sothuchi-60672-firebase-adminsdk-6kazn-c16a300118.json";
        FileInputStream serviceAccount = new FileInputStream(serviceAccountFile);
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(linkUrl)
                .build();
        FirebaseApp.initializeApp(options);
    }

    public CompletableFuture<ArrayList<User>> getUsers() throws IOException {
        CompletableFuture<ArrayList<User>> future = new CompletableFuture<>();
        try {
            initFirebase();
            DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("users");
            dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<User> users = new ArrayList<>();
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

    public void saveUser(User user){
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("users");
        dataRef.child(user.getId()).setValueAsync(user);
    }

    public void updateUser(User user){
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("users");
        dataRef.child("user" + user.getId()).setValueAsync(user);
    }
}
