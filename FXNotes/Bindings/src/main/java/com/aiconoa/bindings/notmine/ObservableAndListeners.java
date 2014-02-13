/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aiconoa.bindings.notmine;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * From http://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm
 */
public class ObservableAndListeners {

    public static void main(String[] args) {

        Bill bill1 = new Bill();
        Bill bill2 = new Bill();
        Bill bill3 = new Bill();

        NumberBinding total = Bindings.add(
                bill1.amountDueProperty().add(bill2.amountDueProperty()),
                bill3.amountDueProperty());

        
        total.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {
                System.out.println("The binding is now invalid.");
            }
        });
        
        //Attention, un ChangeListener evalue le binding de maniere eager !
        total.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                System.out.println("Total changed. Old value: " + t + " New value: " + t1);
            }
        });

        // First call makes the binding invalid
        bill1.setAmountDue(200.00);
        // The binding is now invalid
        bill2.setAmountDue(100.00);
        bill3.setAmountDue(75.00);

        // Make the binding valid...
        System.out.println(total.getValue());

        // Make invalid... 
        bill3.setAmountDue(150.00);

        // Make valid...
        System.out.println(total.getValue());
        
                
                
//        double d1 = 1;
//        double d2 = 0;
//        double d3 = d1 / d2;
//        System.out.println("d3 = " + d3);
//        
//        IntegerProperty i1 = new SimpleIntegerProperty();
//        IntegerProperty i2 = new SimpleIntegerProperty();
//        NumberBinding avg = Bindings.divide(i1.add(i2),0);
//        System.out.println(avg.getValue());

    }
}
