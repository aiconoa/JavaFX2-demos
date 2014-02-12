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
package com.aiconoa.fxnotes.listcell;

import com.aiconoa.fxnotes.model.Note;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

/**
 *
 * @author Thomas
 */
public class NoteCell extends ListCell<Note> {

    VBox vbox = new VBox();
    Label titleLabel = new Label("(note title)");
    Label metaLabel = new Label("(note metas)");
    Label contentExtractLabel = new Label("(note content)");

    public NoteCell() {
        super();
        this.setPrefWidth(0);
        titleLabel.setStyle("-fx-font-weight:bold");
        metaLabel.setStyle("-fx-text-fill:green");
        contentExtractLabel.setWrapText(true);
        contentExtractLabel.setPrefHeight(50);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setFillWidth(true);
        vbox.getChildren().addAll(titleLabel, metaLabel, contentExtractLabel);
        //VBox.setVgrow(titleLabel, Priority.NEVER);
        //VBox.setVgrow(contentExtractLabel, Priority.NEVER);
        this.getChildren().clear();
    }

    @Override
    protected void updateItem(Note note, boolean empty) {
        super.updateItem(note, empty);

        titleLabel.textProperty().unbind();
        contentExtractLabel.textProperty().unbind();

        if (!empty) {
            titleLabel.textProperty().bind(note.titleProperty());
            contentExtractLabel.textProperty().bind(note.contentProperty());
            metaLabel.setText(todayOrSimpleFormat(note.getUpdatedOn()));
            setGraphic(vbox);
        } else {
            setGraphic(null);
        }
    }

    private String todayOrSimpleFormat(Date d) {
        if (d == null) {
            return "<<null>>";
        }
        Calendar dCal = Calendar.getInstance();
        dCal.setTime(d);

        Calendar today = Calendar.getInstance();
        boolean sameDay = dCal.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                && dCal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR);

        if (sameDay) {
            return "Today";
        }
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yy");
        return formater.format(d);
    }
}
