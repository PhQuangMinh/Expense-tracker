package org.example.projectassignment;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class TransactionImporter {
    public List<Transaction> importTransaction (){
        List <Transaction> transactions = new ArrayList<>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("FileBinary.in"))){
            while(true){
                try{
                    Transaction transaction = (Transaction) ois.readObject();
                    transactions.add(transaction);
                }
                catch(EOFException e){
                    break;
                }
            }
        }
        catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return transactions;
    }
}
