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
package com.aiconoa.concurrency;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

public class FXMLController implements Initializable {

    static final int max = 100;
    @FXML
    protected Label status, stateLabel;
    @FXML
    protected Button clickMe, startButton, restartButton, resetButton, cancelButton;
    @FXML
    protected ProgressBar progressBar;
    @FXML
    protected ProgressIndicator progressIndicator;
    protected SynchronizationService syncService = new SynchronizationService();

    @FXML
    private void handleButtonAction(ActionEvent event) {
        createAndRunNewTask();
    }

    @FXML
    private void handleButtonStart(ActionEvent event) {
        syncService.start();
    }

    @FXML
    private void handleButtonRestart(ActionEvent event) {
        syncService.restart();
    }

    @FXML
    private void handleButtonReset(ActionEvent event) {
        syncService.reset();
    }

    @FXML
    private void handleButtonCancel(ActionEvent event) {
        syncService.cancel();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        stateLabel.textProperty().bind(Bindings.convert(syncService.stateProperty()));

        progressIndicator.progressProperty().bind(syncService.progressProperty());

        startButton.disableProperty().bind(
                Bindings.notEqual(Service.State.READY, syncService.stateProperty()));
        restartButton.disableProperty().bind(
                Bindings.notEqual(Service.State.RUNNING, syncService.stateProperty()));

        BooleanBinding b = new BooleanBinding() {
            protected List<Worker.State> enabled = Arrays.asList( Service.State.SUCCEEDED, 
                                                                  Service.State.FAILED, 
                                                                  Service.State.CANCELLED, 
                                                                  Service.State.READY);
            
            { super.bind(syncService.stateProperty()); }
            @Override
            protected boolean computeValue() {
                return ! enabled.contains(syncService.getState());
            }
        };
        resetButton.disableProperty().bind(b);
        
        status.textProperty().bind(Bindings.format("%.0f of %.0f objects have been synchronized", 
                Bindings.when(syncService.workDoneProperty().lessThan(0)).
                        then(0).
                        otherwise(syncService.workDoneProperty()),
                Bindings.when(syncService.totalWorkProperty().lessThan(0))
                        .then(0)
                        .otherwise(syncService.totalWorkProperty())));
        
// Or you can have some fun with the bindings
//        resetButton.disableProperty().bind(
//                Bindings.not(
//                Bindings.or(
//                Bindings.or(
//                Bindings.equal(Service.State.SUCCEEDED, syncService.stateProperty()),
//                Bindings.equal(Service.State.FAILED, syncService.stateProperty())),
//                Bindings.or(
//                Bindings.equal(Service.State.CANCELLED, syncService.stateProperty()),
//                Bindings.equal(Service.State.READY, syncService.stateProperty())))));

//        resetButton.disableProperty().bind(
//                Bindings.createBooleanBinding(new Callable<Boolean>() {
//            protected List<Worker.State> enabled = Arrays.asList(Service.State.SUCCEEDED, Service.State.FAILED, Service.State.CANCELLED, Service.State.READY);
//
//            @Override
//            public Boolean call() throws Exception {
//
//                return ! enabled.contains(syncService.stateProperty().get());
//            }
//        }, syncService.stateProperty()));

/* 
        Exemple ecoute etat de plusieurs services
 List<Service> l;
        
        BooleanProperty b = new SimpleBooleanProperty(true);
        for(Service s: l) {
            b = b.and(s.stateProperty().equals(Service.State.SUCCEEDED));
        }
        
        label.visibleProperty().bind(b);
                
        
        finishedProperty().bind(Bindings.and(syncService.stateProperty().equals(Service.State.SUCCEEDED), syncService2.progressProperty().isEqualTo(1))) 
        */        
        
    }

    protected void createAndRunNewTask() {
        clickMe.setDisable(true);

        Task task = new Task<Void>() {
            @Override
            public Void call() {

                for (int i = 1; i <= max; i++) {
                    if (isCancelled()) {
                        break;
                    }
                    updateProgress(i, max);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return null;
            }
        };
        progressBar.progressProperty().bind(task.progressProperty());       
     
        task.setOnSucceeded(new EventHandler() {
            @Override
            public void handle(Event t) {
                clickMe.setDisable(false);
            }
        });
        task.setOnCancelled(new EventHandler() {
            @Override
            public void handle(Event t) {
                clickMe.setDisable(true);
            }
        });
        
        
        new Thread(task).start();
        
    }
}

class SynchronizationService extends Service<Integer> {
    @Override
    protected Task<Integer> createTask() {
        return new Task<Integer>() {
            @Override
            public Integer call() {
                int i = 1;
                for (; i <= FXMLController.max; i++) {
                    if (isCancelled()) {
                        break;
                    }

                    updateMessage("synchronizing " + i);
                    updateProgress(i, FXMLController.max);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return i;
            }
        };
    }

}
