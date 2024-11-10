package org.example.projectassignment.controller.firebase;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.example.projectassignment.model.category.CategoryModel;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.*;


public class FirebaseCategory {
    private final String nameBucket = "proptitlocktech.appspot.com";
    private Storage storage;

    public void init() throws IOException {
        String locationFile = "proptitlocktech-firebase-adminsdk-xp853-fd3056ffef.json";
        FileInputStream serviceAccountFile = new FileInputStream(locationFile);
        storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountFile))
                .build()
                .getService();
    }

    private Image convertBoldToImage(Blob blob) {
        if (blob != null) {
            byte[] blobData = blob.getContent();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(blobData);
            return new Image(inputStream);
        }
        return null;
    }

    public List<CategoryModel> getListCategory(String folderName) throws IOException {
        init();
        Page<Blob> blobs = storage.list(nameBucket, Storage.BlobListOption.prefix(folderName));
        List<CategoryModel> listCategories = new ArrayList<>();
        boolean checkFolder = false;
        for (Blob blob : blobs.iterateAll()) {
            if (!checkFolder){
                checkFolder = true;
                continue;
            }
            int lastSlashIdx = blob.getName().lastIndexOf('/');
            CategoryModel category = new CategoryModel(blob.getName().substring(lastSlashIdx+1, lastSlashIdx+3), convertBoldToImage(blob));
            listCategories.add(category);
        }
        Collections.sort(listCategories);
        return listCategories;
    }


}
