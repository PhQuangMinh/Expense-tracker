package org.example.projectassignment;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.projectassignment.controller.feature.input.category.ManagerCategory;
import org.example.projectassignment.controller.firebase.FirebaseUser;
import org.example.projectassignment.model.user.informationuser.User;
import org.example.projectassignment.view.auth.signin.SignIn;
import org.example.projectassignment.view.utils.WaitStage;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InitApp {
    private  FirebaseUser firebaseUser;
    WaitStage waitStage = new WaitStage();

    private final int scores = Runtime.getRuntime().availableProcessors();
    public final ExecutorService executorService = Executors.newFixedThreadPool(scores*2);

    public InitApp(){

    }

    public void init(Stage stage) throws IOException {
        Instant start = Instant.now();
        waitStage.show();
        firebaseUser = new FirebaseUser();
        CompletableFuture<ManagerCategory> managerCategoryFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return new ManagerCategory();
            } catch (IOException | ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, executorService);

        CompletableFuture<List<User>> listUsersFuture = firebaseUser.getUsers();
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(managerCategoryFuture, listUsersFuture);
        combinedFuture.thenRun(() -> {
            try {
                ManagerCategory managerCategory = managerCategoryFuture.get();
                List<User> users = listUsersFuture.get();
                Platform.runLater(() -> {
                    FXMLLoader loader = new FXMLLoader(SignIn.class.getResource("signin.fxml"));
                    try {
                        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("view/Image/logo.png")));
                        Parent root = loader.load();
                        SignIn signIn = loader.getController();
                        signIn.init(users, managerCategory);
                        Scene scene = new Scene(root, 600, 750);
                        stage.setTitle("Sổ thu chi");
                        stage.setScene(scene);
                        stage.getIcons().add(image);
                        stage.show();
                        stage.setOnCloseRequest(event -> {
                            firebaseUser.saveUser(users);
                        });
                        stage.setResizable(false);
                        waitStage.close();
                        System.out.println(Instant.now().toEpochMilli() - start.toEpochMilli());
                    }catch (IOException e){
                        throw new RuntimeException(e);
                    }
                });
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
