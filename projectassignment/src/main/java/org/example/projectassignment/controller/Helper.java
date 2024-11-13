package org.example.projectassignment.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

import java.text.NumberFormat;
import java.util.Locale;

public class Helper {
    public void setAmountField(TextField amountField) {
        amountField.textProperty().addListener(new ChangeListener<String>() {
            private boolean changing = false;

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (changing) {
                    return;
                }
                changing = true;
                try{
                    if(newValue == null || newValue.isEmpty()){
                        amountField.setText("") ;
                    }
                    else{
                        String cleanString = removeCommas(newValue);
                        double parsed = Double.parseDouble(cleanString) ;
                        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
                        formatter.setGroupingUsed(true);
                        String formattedString = formatter.format(parsed);
                        amountField.setText(formattedString);
                    }
                }
                catch(NumberFormatException e){
                    amountField.setText(oldValue);
                }
                changing = false;
            }
        });
    }

    public String removeCommas(String input){
        return input.replace("," , "") ;
    }
}
