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

import com.aiconoa.fxnotes.model.Note;
import com.aiconoa.fxnotes.model.Notebook;
import com.aiconoa.fxnotes.uicontrol.listcell.NoteCell;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;

/**
 *
 * @author Thomas
 */
public class SnippetNotebookViewer extends NotebookViewerBase implements Initializable {

    @FXML
    private ListView<Note> noteListView;

    public SnippetNotebookViewer() {
        super("SnippetNotebookViewer.fxml");
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        noteListView.setCellFactory(new Callback<ListView<Note>, ListCell<Note>>() {

            @Override
            public ListCell<Note> call(ListView<Note> p) {
                return new NoteCell();
            }
        });

        noteListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Note>() {
            @Override
            public void changed(ObservableValue<? extends Note> ov, Note oldValue, Note newValue) {
                setNote(newValue);
            }
        });

        noteListView.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                Dragboard db = noteListView.startDragAndDrop(TransferMode.ANY);
                /* Put an object on a dragboard */
                ClipboardContent content = new ClipboardContent();
                //http://docs.oracle.com/javafx/2/drag_drop/jfxpub-drag_drop.htm
                content.putString(noteListView.getSelectionModel().getSelectedItem().getId());
                // will crash because JavaFX Properties aren't serializable
                //https://forums.oracle.com/thread/2385099
                //content.put(new DataFormat("com.aiconoa.fxnotes.Note"), noteListView.getSelectionModel().getSelectedItem());
                db.setContent(content);

                event.consume();
            }
        });
    }

    @Override
    public void setNotebook(Notebook notebook) {
        super.setNotebook(notebook);
        if (notebook == null) {
            noteListView.setItems(null);
        } else {
            noteListView.setItems(notebook.getNotes());
        }
    }
}