/*
 * The MIT License (MIT)
 *
 * Copyright 2014 AICONOA.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.aiconoa.bindings.apis;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author Thomas
 */
public class JavaFXProperty {
    
    private DoubleProperty sizeProperty = new SimpleDoubleProperty(this, "size", 2) {

        /**
         * called each time the property is invalidated
         */
        @Override
        protected void invalidated() {
            super.invalidated();
        }
        
    };
    public double getSize() {
        return sizeProperty.get();
    }
    public void setSize(double size) {
        sizeProperty.set(size);
    }
    public DoubleProperty sizeProperty() {
        return sizeProperty;
    }
    
    
           
}
