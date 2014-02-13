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

import com.aiconoa.fxnotes.model.HTMLNote;
import com.aiconoa.fxnotes.model.Note;
import com.aiconoa.fxnotes.model.Notebook;
import com.aiconoa.fxnotes.model.VideoNote;
import com.aiconoa.fxnotes.uicontrol.note.HTMLNoteEditor;
import com.aiconoa.fxnotes.uicontrol.note.VideoNoteEditor;
import com.aiconoa.fxnotes.uicontrol.note.NoteEditorBase;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Thomas
 */
abstract public class NotebookViewerBase extends SplitPane implements Initializable {

    protected Notebook notebook;
    protected ObjectProperty<Note> noteProperty;

    @FXML
    protected AnchorPane noteEditorAnchorPane;

    protected NoteEditorBase noteEditor;

    public NotebookViewerBase(String fxmlScript) {
        try {
            if (fxmlScript == null) {
                throw new InstantiationException("fxmlScript cannot be null");
            }
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlScript));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);

            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
            noteProperty = new SimpleObjectProperty<>();
        } catch (InstantiationException ex) {
            Logger.getLogger(NotebookViewerBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        noteEditor = new HTMLNoteEditor();

        AnchorPane.setTopAnchor(noteEditor, 0.0);
        AnchorPane.setLeftAnchor(noteEditor, 0.0);
        AnchorPane.setRightAnchor(noteEditor, 0.0);
        AnchorPane.setBottomAnchor(noteEditor, 0.0);

        noteEditorAnchorPane.getChildren().add(noteEditor);
    }

    public void setNotebook(Notebook notebook) {
        if (this.notebook == null && notebook == null) {
            return;
        }
        if (this.notebook != null && this.notebook.equals(notebook)) {
            return;
        }

        this.notebook = notebook;
    }

    public Note getNote() {
        return this.noteProperty.get();
    }

    public void setNote(Note note) {
        if (this.noteProperty.get() == null && note == null) {
            return;
        }
        if (this.noteProperty.get() != null && this.noteProperty.get().equals(note)) {
            return;
        }

        if (note != null) {
            if (note instanceof HTMLNote) {
                noteEditor = new HTMLNoteEditor();
            } else if (note instanceof VideoNote) {
                noteEditor = new VideoNoteEditor();
            }
            AnchorPane.setTopAnchor(noteEditor, 0.0);
            AnchorPane.setLeftAnchor(noteEditor, 0.0);
            AnchorPane.setRightAnchor(noteEditor, 0.0);
            AnchorPane.setBottomAnchor(noteEditor, 0.0);

            noteEditorAnchorPane.getChildren().add(noteEditor);

            setNotebook(note.getNotebook());
        }

        this.noteProperty.set(note);

        noteEditor.setNote(note);
    }

    public ObjectProperty<Note> noteProperty() {
        return this.noteProperty;
    }
}
