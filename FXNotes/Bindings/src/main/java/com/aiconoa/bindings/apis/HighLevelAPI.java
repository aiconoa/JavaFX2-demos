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

import static javafx.beans.binding.Bindings.concat;
import static javafx.beans.binding.Bindings.add;
import static javafx.beans.binding.Bindings.multiply;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author AICONOA
 */
public class HighLevelAPI {
    public static void main(String[] args) {
        
        IntegerProperty a = new SimpleIntegerProperty(3);
        IntegerProperty b = new SimpleIntegerProperty(4);
        IntegerProperty c = new SimpleIntegerProperty(5);
        
        // 1, using Bindings class static methods
        NumberBinding d1 = multiply(add(a, b), c);   // (a + b) * c
        
        // 2, fluent interface
        NumberBinding d2 = a.add(b).multiply(c);
        
       // 3, mixing stuff
        NumberBinding d3 = multiply(a.add(b), c);
        
        // Conversions
        StringBinding s1 = a.asString("a = %d");
        System.out.println(s1.get()); // 3
        a.set(5);
        System.out.println(s1.get()); // 5
        
        // Concatenation
        StringProperty s2 = new SimpleStringProperty("Hello");
        StringProperty s3 = new SimpleStringProperty("World");
        StringProperty s4 = new SimpleStringProperty("!");
        
        StringExpression s5 = s2.concat(" ").concat(s3).concat(" ").concat(s4);
        System.out.println(s5.get()); // Hello World !
        s3.set("the");
        s4.set("earth");
        System.out.println(s5.get()); // Hello the earth
        
        concat("Hello", " ", "the", "", "moon");
        
        // Ternary
        //cond?value1:value2
//        Bindings.when(cond)
//                .then(value1)
//                .otherwise(value2)
        
        
    }
}
