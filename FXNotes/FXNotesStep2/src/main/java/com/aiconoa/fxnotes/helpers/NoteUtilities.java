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
package com.aiconoa.fxnotes.helpers;

import de.svenjacobs.loremipsum.LoremIpsum;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.aiconoa.fxnotes.model.HTMLNote;
import com.aiconoa.fxnotes.model.Note;
import com.aiconoa.fxnotes.model.Notebook;

/**
 * Generates notebooks and notes
 * @author Thomas
 */
public class NoteUtilities {
    public static List<Notebook> generatedNotebooks;
    
    public static List<Notebook> generateNotebooks() {
        List<Notebook> notebooks = new ArrayList<>();

        LoremIpsum loremIpsum = new LoremIpsum();
        Random rand = new Random();

        for (int i = 0; i < rand.nextInt(10) + 2; i++) {
            Notebook notebook = new Notebook("notebook " + i);
            
            for (int j = 0; j < rand.nextInt(30) + 2; j++) {
                //TODO randomize the notes types
                Note note = new HTMLNote(notebook);
                note.getUpdatedOn().setYear(rand.nextBoolean() ? 113 : 112);
                note.setTitle("Note number " + i + " - " + j);
                note.setContent(loremIpsum.getWords(rand.nextInt(2000) + 1));
                //notebook.addNote(note);
            }
            
            notebooks.add(notebook);
        }
        NoteUtilities.generatedNotebooks = notebooks;
        return notebooks;
    }
    
    public static List<Note> findAllNotes() {
        return findAllNotes(generatedNotebooks);
    }
    
    public static List<Note> findAllNotes(List<Notebook> notebooks) {
        List<Note> notes = new ArrayList<>();
        for(Notebook notebook: notebooks) {
            notes.addAll(notebook.getNotes());
        }
        
        return notes;
    }    
}
