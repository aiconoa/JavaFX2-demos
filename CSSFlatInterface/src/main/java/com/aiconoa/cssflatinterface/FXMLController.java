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
package com.aiconoa.cssflatinterface;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class FXMLController implements Initializable {

    @FXML
    private GridPane gridPane;
    
    @FXML
    private Pane gaugePane;
    
    @FXML
    private Pane thresoldPane;

    @FXML
    private LineChart lineChart;
    
    @FXML
    private TextArea credits;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initGaugeCanvas();
        
        StringBuilder sb = new StringBuilder();
        sb.append("Credits\n");
        sb.append("Flat interface inspired by http://www.youtube.com/watch?v=kLWz0RV-btc\n");
        sb.append("\nIcons from thenounproject.com\n");
        sb.append("Minus designed by P.J. Onori from the Noun Project.\n");
        sb.append("Plus designed by P.J. Onori from the Noun Project.\n");
        sb.append("Reload designed by S©tt Green - the Noun Project - Public Domain.\n");
        sb.append("Power - the Noun Project - Public Domain.");
                
        credits.setText(sb.toString());
    }    
    
    @FXML
    protected void handleQuit(ActionEvent event) {
        Platform.exit();
    }
    
    protected void initGaugeCanvas() {
        gaugePane.setId("gaugePane");
        
        Canvas canvas = new Canvas(gaugePane.getWidth(), gaugePane.getHeight());

        GraphicsContext gc = canvas.getGraphicsContext2D();

        canvas.widthProperty().bind(gaugePane.widthProperty());
        canvas.heightProperty().bind(gaugePane.heightProperty());

        final ResizeChangeListener resizeChangeListener = new ResizeChangeListener(gaugePane, gc);

        canvas.widthProperty().addListener(resizeChangeListener);
        canvas.heightProperty().addListener(resizeChangeListener);
        gaugePane.getChildren().add(canvas);
    }
 
    private class ResizeChangeListener implements ChangeListener<Number> {

        private final Pane parent;
        private final GraphicsContext gc;

        public ResizeChangeListener(Pane parent, GraphicsContext gc) {
            this.parent = parent;
            this.gc = gc;
        }

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            final double width = parent.getWidth();
            final double height = parent.getHeight();
            gc.clearRect(0, 0, width, height);

            gc.setFill(Color.WHITE);
            gc.setStroke(Color.WHITE);
            gc.setLineCap(StrokeLineCap.ROUND);
            gc.setLineWidth(20);
            gc.strokeArc(width / 2 - width / 4, height / 2 - height / 4, width / 2, height / 2, -45, 270, ArcType.OPEN);

            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);
            
            gc.setFont(new Font("IMPACT", 60));
            gc.fillText(
                    "21",
                    Math.round(width / 2),
                    Math.round(height / 2)
            );
            
            gc.setFont(new Font("IMPACT", 30));
            gc.fillText(
                    "°C",
                    Math.round(width / 2),
                    Math.round(height / 2 + height / 5)
            );

        }
    }
}