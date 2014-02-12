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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Thomas
 */
public class NotebookTableListViewerBase extends NotebookViewerBase implements Initializable {

    @FXML
    protected TableView<Note> tableView;

    public NotebookTableListViewerBase(String fxmlScript) {
        super(fxmlScript);
    }
        
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Note>() {
            @Override
            public void changed(ObservableValue<? extends Note> observable, Note oldValue, Note newValue) {
                setNote(newValue);
            }
        });
        
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @Override
    public void setNotebook(Notebook notebook) {
        super.setNotebook(notebook);
        if(notebook != null) {
            tableView.setItems(notebook.getNotes());
        } else {
            tableView.setItems(null);
        }
    }

    @Override
    public void setNote(Note note) {
        super.setNote(note);

        //have to clear the selection, otherwise the note matching the old index is still grey
        if(tableView.getItems() != null) {
            tableView.getSelectionModel().clearAndSelect(tableView.getItems().indexOf(noteProperty.get()));
        }
    }

    protected void buildTableColumnFromNote(int num, String property) {
        TableColumn col = tableView.getColumns().get(num);
        col.setCellValueFactory(new PropertyValueFactory<Note, String>(property));
    }
}