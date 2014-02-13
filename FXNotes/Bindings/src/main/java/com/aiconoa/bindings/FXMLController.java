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
package com.aiconoa.bindings;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;

public class FXMLController implements Initializable {

    public static List<String> colorNames = 
        Arrays.asList("chocolate", "salmon", "gold", "coral", 
                      "darkorchid", "darkgoldenrod", "lightsalmon", 
                      "black", "rosybrown", "blue","blueviolet", "brown");
    @FXML
    protected Label wordLabel;
    @FXML
    protected TextField textField;
    @FXML
    protected Button updateButton;
    @FXML
    protected Circle circle;
    @FXML
    protected Rectangle rectangle;
    @FXML
    protected Label sizeLabel;
    @FXML
    protected Label colorLabel;
    @FXML
    protected Slider sizeSlider;
    @FXML
    protected Slider colorSlider;
    @FXML
    protected ChoiceBox<String> choiceBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTextFieldLabelBindings();
        initializeRectangleCircleSizeBinding();
        initializeColorSliderBindings();
        initializeChoiceBoxBindings();
    }

    protected void initializeTextFieldLabelBindings() {
        final StringProperty word = new SimpleStringProperty("Thomas");
        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                word.set("JavaFX");
            }
        });
        wordLabel.textProperty().bind(word);
        textField.textProperty().bindBidirectional(word);
        
        
        updateButton.effectProperty().bind(
                Bindings.when(updateButton.hoverProperty())
                        .then(new InnerShadow(20, Color.RED)).
                        otherwise((InnerShadow) null));
    }
    
    protected void initializeRectangleCircleSizeBinding() {
        rectangle.heightProperty().bind(sizeSlider.valueProperty().multiply(4));
        circle.radiusProperty().bind(rectangle.heightProperty());
        sizeLabel.textProperty().bind(sizeSlider.valueProperty().asString("%.1f"));
        sizeSlider.setValue(20);    
    }
    
    protected void initializeColorSliderBindings() {
        colorSlider.setMax(colorNames.size() - 1); //colorSlider.maxProperty().bind(colorNames.sizeProperty);

        StringBinding colorNameBinding = new StringBinding() {
            {
                super.bind(colorSlider.valueProperty());
            }

            @Override
            protected String computeValue() {
                return colorNames.get((int) colorSlider.getValue());
            }
        };

        colorLabel.textProperty().bind(colorNameBinding);

        ObjectBinding colorBinding = new ObjectBinding() {
            {
                super.bind(colorSlider.valueProperty());
            }

            @Override
            protected Object computeValue() {
                return Color.web(colorNames.get((int) colorSlider.getValue()));
            }
        };

        rectangle.fillProperty().bind(colorBinding);
        circle.fillProperty().bind(rectangle.fillProperty());      
    }
    
    protected void initializeChoiceBoxBindings() {
        final ObservableList<String> items = 
                FXCollections.observableArrayList(colorNames);
        choiceBox.setItems(items); 
        
        //https://forums.oracle.com/message/10775242
        Bindings.bindBidirectional(choiceBox.valueProperty(), 
                                   colorSlider.valueProperty(), 
                                   new StringConverter<Number>() {
            @Override
            public String toString(Number t) {
                return colorNames.get(t.intValue());
            }

            @Override
            public Number fromString(String string) {
                return new Double(colorNames.indexOf(string));
            }
        });
        
//        BUG here, cf parseObject        
//        Bindings.bindBidirectional(choiceBox.valueProperty(), colorSlider.valueProperty(), new Format() {
//
//            @Override
//            public StringBuffer format(Object o, StringBuffer sb, FieldPosition fp) {
//                return sb.append(colorNames.get(((Double) o).intValue()));
//            }
//
//            @Override
//            public Object parseObject(String string, ParsePosition pp) {
              // bug => the value returned here is good but the control receives 0
//                return new Double(colorNames.indexOf(string));
//            }
//        });       
    }
}
