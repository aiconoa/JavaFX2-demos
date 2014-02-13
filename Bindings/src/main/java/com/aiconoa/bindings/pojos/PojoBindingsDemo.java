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
package com.aiconoa.bindings.pojos;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;

/**
 *
 * @author Thomas
 */
public class PojoBindingsDemo extends Application {

    @Override
    public void start(Stage primaryStage) throws NoSuchMethodException {
        //Illustrates s how to bind to a Java Bean's properties without modifying the Java Bean with FX Properties.
        Pojo pojo = new Pojo();
        pojo.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                System.out.println("pce fired: " + pce.getOldValue() + " // " + pce.getNewValue() );
            }
        });
        
        // http://docs.oracle.com/javafx/2/api/javafx/beans/property/adapter/JavaBeanObjectProperty.html        
        JavaBeanObjectProperty p1 = JavaBeanObjectPropertyBuilder.create().bean(pojo).name("prop1").build();
        // Supports Java Bean bound properties !!!
        JavaBeanObjectProperty p2 = JavaBeanObjectPropertyBuilder.create().bean(pojo).name("prop2").build();
        
//        p.addListener(new ChangeListener<Pojo>() {
//
//            @Override
//            public void changed(ObservableValue<? extends Pojo> ov, Pojo t, Pojo t1) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//        });

        ChangeListener c = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                System.out.println("old value: " + t);
                System.out.println("new value: " + t1);
            }
        };

        SimpleStringProperty ssp1 = new SimpleStringProperty();
        ssp1.bind(p1);
        ssp1.addListener(c);

        SimpleStringProperty ssp2 = new SimpleStringProperty();
        ssp2.bind(p2);
        ssp2.addListener(c);

        p1.setValue("test 1 prop 1");
        pojo.setProp1("test 2 prop 1 ");
        //System.out.println(pojo.getProp1());
        //

        p2.setValue("test 1 prop 2");
        pojo.setProp2("test 2 prop 2");
        
        Platform.exit();
        
        
        // See also BeanPathAdapter 
        // http://code.google.com/p/javafx-demos/source/browse/trunk/javafx-demos/src/main/java/com/ezest/javafx/beanpathadapter/BeanPathAdapter.java
        // http://ugate.wordpress.com/2012/06/14/javafx-programmatic-pojo-bindings/
        
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
