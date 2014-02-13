/**
 * From http://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm
 */
package com.aiconoa.bindings.notmine;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

class Bill {

    // Define the property
    private DoubleProperty amountDue = new SimpleDoubleProperty();

    // Define a getter for the property's value
    public double getAmountDue() {
        return amountDue.get();
    }

    // Define a setter for the property's value
    public void setAmountDue(double value) {
        amountDue.set(value);
    }

    // Define a getter for the property itself
    public DoubleProperty amountDueProperty() {
        return amountDue;
    }
}