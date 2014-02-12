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

import com.aiconoa.fxnotes.model.HTMLNote;
import com.aiconoa.fxnotes.model.Note;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;

/**
 *
 * @author Thomas
 */
public class HTMLNoteEditor extends NoteEditorBase {

    @FXML
    private HTMLEditor noteContentHTMLEditor;

    public HTMLNoteEditor() {
        super("HTMLNoteEditor.fxml");
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        noteContentHTMLEditor.setDisable(note != null);
    }

    @Override
    public void setNote(Note newNote) {
        if (newNote != null
                && !(newNote instanceof HTMLNote)) {
            throw new IllegalArgumentException("Note must be an HTMLNote");
        }

        super.setNote(newNote);

        if (newNote != null) {
            noteContentHTMLEditor.setHtmlText(newNote.getContent().toString());
            noteContentHTMLEditor.setDisable(false);
        } else {
            noteContentHTMLEditor.setHtmlText("");
            noteContentHTMLEditor.setDisable(true);
        }

        noteContentHTMLEditor.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                note.setContent(noteContentHTMLEditor.getHtmlText());
            }
        });
    }
}
