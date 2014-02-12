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
package com.aiconoa.fxnotes.uicontrol.notebook;

import com.aiconoa.fxnotes.uicontrol.listcell.NoteCell;
import com.aiconoa.fxnotes.model.Note;
import com.aiconoa.fxnotes.model.Notebook;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Thomas
 */
public class SnippetNotebookViewer extends SplitPane implements Initializable {

    @FXML
    private ListView<Note> noteListView;    
    @FXML
    private TextField noteTitleTextField;
    @FXML
    private HTMLEditor noteContentHTMLEditor;

    private Note selectedNote;
    
    public SnippetNotebookViewer() {
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load(getClass().getResourceAsStream("SnippetNotebookViewer.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(SnippetNotebookViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    @FXML 
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        noteListView.setCellFactory(new Callback<ListView<Note>, ListCell<Note>>() {

            @Override
            public ListCell<Note> call(ListView<Note> p) {
                return new NoteCell();
            }
        });

        noteListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Note>() {

            @Override
            public void changed(ObservableValue<? extends Note> ov, Note oldValue, Note newValue) {
                if(oldValue != null) {
                    noteTitleTextField.textProperty().unbindBidirectional(
                            oldValue.titleProperty());
                }
                
                if(newValue != null) {
                    noteTitleTextField.textProperty().bindBidirectional(newValue.titleProperty());
                    noteTitleTextField.setDisable(false);
                    noteContentHTMLEditor.setHtmlText(newValue.getContent().toString());                    
                    noteContentHTMLEditor.setDisable(false);    
                } else {
                    noteTitleTextField.setText("");
                    noteTitleTextField.setDisable(true);
                    noteContentHTMLEditor.setHtmlText("");
                    noteContentHTMLEditor.setDisable(true);
                }

                selectedNote = newValue;
            }
        });

        noteContentHTMLEditor.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                selectedNote.setContent(noteContentHTMLEditor.getHtmlText());
            }
        });
    }

    public void setNotebook(Notebook notebook) {
         noteListView.setItems(notebook.getNotes());        
    }
}