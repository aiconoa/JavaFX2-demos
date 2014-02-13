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
package com.aiconoa.fxnotes.uicontrol.note;

import com.aiconoa.fxnotes.model.Note;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author Thomas
 */
// Extending VBox might not be the best choice as I'm building a control and not a layout. 
// But this is the most convenient way to build a control with an existing layout in JavaFX...
public class NoteEditorBase extends VBox implements Initializable {

    protected Note note;
    
    @FXML
    protected TextField noteTitleTextField;

    public NoteEditorBase(String fxmlScript) {
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
        } catch (InstantiationException ex) {
            Logger.getLogger(NoteEditorBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        noteTitleTextField.setDisable(note != null);
    }

    public void setNote(Note newNote) {
        if (note != null) {
            noteTitleTextField.textProperty().unbindBidirectional(note.titleProperty());
        }

        if (newNote != null) {
            noteTitleTextField.textProperty().bindBidirectional(newNote.titleProperty());
            noteTitleTextField.setDisable(false);
        } else {
            noteTitleTextField.setText("");
            noteTitleTextField.setDisable(true);
        }
        note = newNote;
    }
}