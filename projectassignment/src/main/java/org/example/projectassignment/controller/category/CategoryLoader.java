package org.example.projectassignment.controller.category;

import org.example.projectassignment.controller.firebase.FirebaseCategory;
import org.example.projectassignment.model.category.CategoryModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class CategoryLoader {
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    private final FirebaseCategory firebaseCategory;

    public CategoryLoader() {
        this.firebaseCategory = new FirebaseCategory();
    }

    private CompletableFuture<List<CategoryModel>> getListCategoryAsync(String folderName) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return firebaseCategory.getListCategory(folderName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, executorService);
    }

    public List<CategoryModel> getListCategoryModels() throws ExecutionException, InterruptedException {
        String[] pathFolders = new String[]{"01", "02", "03", "04", "05", "06", "07"};
        List<CompletableFuture<List<CategoryModel>>> futures = new ArrayList<>();
        for (String folder : pathFolders){
            futures.add(getListCategoryAsync("newcategory/image" + folder));
        }
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .flatMap(future -> {
                            try {
                                return future.get().stream(); // Sử dụng get() để lấy kết quả từ CompletableFuture
                            } catch (InterruptedException | ExecutionException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .collect(Collectors.toList()))
                .get();
    }
}
