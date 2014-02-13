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

import java.net.URL;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Thomas
 */
public class VideoNote extends AbstractNote {
    private StringProperty urlContentProperty;

    public VideoNote(Notebook notebook) {
        super(notebook);
        urlContentProperty = new SimpleStringProperty();
    }

    @Override
    public void setContent(Object content) {
        if(! (content instanceof String)) {
            throw new IllegalArgumentException("Content must be of type String");
        }
        super.setContent(content);
    }
        
    public void setContent(String url) {
        this.setContent(url);
        urlContentProperty.set(url);
    }
    
    public void setContent(URL url) {
        String externalForm = url.toExternalForm();
        this.setContent(externalForm);
        urlContentProperty.set(externalForm);
    }
}
