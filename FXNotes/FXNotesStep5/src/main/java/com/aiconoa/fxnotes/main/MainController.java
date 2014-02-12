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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aiconoa.fxnotes.main;

import com.aiconoa.fxnotes.helpers.NoteUtilities;
import com.aiconoa.fxnotes.model.Note;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import com.aiconoa.fxnotes.model.Notebook;
import com.aiconoa.fxnotes.uicontrol.notebook.NotebookViewerBase;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;

/**
 * @author Thomas
 */
public class MainController implements Initializable {

    /**
     * ** MODELS ***
     */
    private ObservableList<Notebook> observableNotebooks
            = FXCollections.observableList(NoteUtilities.generateNotebooks());
    Map<String, String> controllerIdMap = new HashMap<>();

    {
        controllerIdMap.put("Snippet", "com.aiconoa.fxnotes.uicontrol.notebook.SnippetNotebookViewer");
        controllerIdMap.put("Side list", "com.aiconoa.fxnotes.uicontrol.notebook.SideListNotebookViewer");
        controllerIdMap.put("Top list", "com.aiconoa.fxnotes.uicontrol.notebook.TopListNotebookViewer");
    }

    private Notebook notebook;
    private Note note;

    private ObservableList<Note> shortcuts = FXCollections.<Note>observableArrayList();

    /**
     * ** VIEWS ***
     */
    @FXML
    private ListView<Notebook> notebooksListView;
    @FXML
    private ListView<Note> shortcutsListView;

    @FXML
    private BorderPane borderPane;
    @FXML
    private ChoiceBox<String> notebookViewerChoiceBox;

    private NotebookViewerBase notebookViewer;

    /*
     private Enum<DataFormat> DnDTarget {

     val DndString = new DataFormat("my.custom.dnd.string")
     val DndObject = new DataFormat("my.custom.dnd.object")

     } */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        notebooksListView.setItems(observableNotebooks);
        notebooksListView.getSelectionModel()
                .selectedItemProperty().addListener(
                        new ChangeListener<Notebook>() {
                            @Override
                            public void changed(ObservableValue<? extends Notebook> observable, Notebook oldValue, Notebook newValue) {
                                selectNotebook(newValue);
                            }
                        });

        shortcutsListView.setItems(shortcuts);
        shortcutsListView.setOnDragEntered(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent t) {
                //TODO change styles
            }
        });
        shortcutsListView.setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                /* accept it only if it is not dragged from the same node 
                 * and if it has a string data */
                if (event.getGestureSource() != shortcutsListView
                        && event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    //event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    event.acceptTransferModes(TransferMode.LINK);

                }

                event.consume();
            }
        });
        shortcutsListView.setOnDragDropped(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                Note n = NoteUtilities.findNodeById(event.getDragboard().getString());
                if(! shortcutsListView.getItems().contains(n)) {
                    shortcutsListView.getItems().add(n);
                }
                //System.out.println("setOnDragDropped " + event.getDragboard().getString());
            }
        });

        notebookViewerChoiceBox.getItems().clear();
        notebookViewerChoiceBox.getItems().addAll(controllerIdMap.keySet());
        notebookViewerChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                switchNotebookViewer(t1);
            }
        });

        notebookViewerChoiceBox.getSelectionModel().select(0);
    }

    protected void switchNotebookViewer(String notebookViewerID) {
        try {
            notebookViewer = (NotebookViewerBase) Class.forName(controllerIdMap.get(notebookViewerID)).newInstance();
            notebookViewer.noteProperty().addListener(new ChangeListener<Note>() {

                @Override
                public void changed(ObservableValue<? extends Note> ov, Note oldNote, Note newNote) {
                    MainController.this.selectNote(newNote);
                }
            });
            this.borderPane.setCenter(notebookViewer);
            this.notebookViewer.setNotebook(notebook);
            this.notebookViewer.setNote(note);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected void selectNotebook(Notebook notebook) {
        this.notebook = notebook;
        notebooksListView.getSelectionModel().select(notebook);
        notebookViewer.setNotebook(notebook);
    }

    public void selectNote(Note note) {
        this.note = note;

        notebookViewer.setNote(note);
    }
}

