package org.example.projectassignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.List;

public class TransactionExporter {
    public  void exportTransaction(List<Transaction> transactions , String filePath) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("FileBinary.in"))){
            for(Transaction transaction : transactions){
                oos.writeObject(transaction);
                System.out.println("Successful");
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
