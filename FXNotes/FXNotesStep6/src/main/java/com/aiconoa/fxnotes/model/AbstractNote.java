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
package com.aiconoa.fxnotes.model;

import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Thomas
 */
public abstract class AbstractNote implements Note {

    protected Date createdOn;
    protected Date updatedOn;
    protected Notebook notebook;
    
    protected StringProperty idProperty;
    protected StringProperty titleProperty;
    protected ObjectProperty contentProperty;

    public AbstractNote(Notebook notebook) {
        this.createdOn = new Date();
        this.updatedOn = new Date();

        idProperty = new SimpleStringProperty();
        
        titleProperty = new SimpleStringProperty();
        contentProperty = new SimpleObjectProperty();
        
        if (notebook != null) {
            notebook.addNote(this);
        }
    }

    @Override
    public String getId() {
        return this.idProperty.get();
    }

    @Override
    public void setId(String id) {
        this.idProperty().set(id);
    }

    @Override
    public StringProperty idProperty() {
        return this.idProperty;
    }
    
    /**
     * @return the title
     */
    @Override
    public String getTitle() {
        return this.titleProperty.get();
    }

    /**
     * @param title the title to set
     */
    @Override
    public void setTitle(String title) {
        this.titleProperty.set(title);
    }
    
    @Override
    public StringProperty titleProperty() {
        return this.titleProperty;
    }

    /**
     * @return the content
     */
    @Override
    public Object getContent() {
        return this.contentProperty.get();
    }

    /**
     * @param content the content to set
     */
    @Override
    public void setContent(Object content) {
        this.contentProperty.set(content);
    }

    @Override
    public ObjectProperty contentProperty() {
        return this.contentProperty;
    }

    /**
     * @return the createdOn
     */
    @Override
    public Date getCreatedOn() {
        return createdOn;
    }

    /**
     * @param createdOn the createdOn to set
     */
    @Override
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * @return the updatedOn
     */
    @Override
    public Date getUpdatedOn() {
        return updatedOn;
    }

    /**
     * @param updatedOn the lastEditedOn to set
     */
    @Override
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public Notebook getNotebook() {
        return this.notebook;
    }

    @Override
    public void setNotebook(Notebook notebook) {
        //if no change, do nothing
        if (this.notebook == null && notebook == null) {
            return;
        }
        if (this.notebook != null && this.notebook.equals(notebook)) {
            return;
        }
        
        
        if(this.notebook != null) {
            this.notebook.removeNote(this);
        }
        
        this.notebook = notebook;
        
        if(this.notebook != null) {
            this.notebook.addNote(this);
        }
    }
}