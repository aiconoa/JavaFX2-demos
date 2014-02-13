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
package com.aiconoa.htmlintegrationmaps;

import com.aiconoa.htmlintegrationmaps.bridge.Bridge;
import com.aiconoa.htmlintegrationmaps.model.Place;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class FXMLController implements Initializable {

    private ObservableList<Place> places = FXCollections.observableArrayList();
    
    @FXML
    private ListView<Place> placesListView;

    @FXML
    private WebView webView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        placesListView.setItems(places);

        places.addAll(
                new Place("Antibes", "@home", 43.5808, 7.1239),
                new Place("Cannes", "The festival", 43.5513, 7.0128),
                new Place("Nice", "Socca + salad", 43.7034, 7.2663)
        );

        webView.getEngine().load(getClass().getResource("/html/map.html")
                                           .toExternalForm());

        webView.getEngine()
               .getLoadWorker()
               .stateProperty()
               .addListener(new ChangeListener<Worker.State>() {

            @Override
            public void changed(ObservableValue<? extends Worker.State> ov, Worker.State t, Worker.State t1) {
                if (t1.equals(Worker.State.SUCCEEDED)) {
                    pageLoadCallback();
                }
            }
        });
    }

    public void pageLoadCallback() {
        JSObject window = (JSObject) webView.getEngine().executeScript("window");
        window.setMember("bridge", new Bridge(placesListView));
            
        for (Place p : places) {
            String script = String.format(Locale.US,
                    "var marker = new google.maps.Marker({"
                    + "    position: new google.maps.LatLng(%f,%f),"
                    + "    map: map,"        
                    + "    title:\"%s\","
                    + "    placenumber:" + places.indexOf(p)
                    + "});",
                    p.getLatitude(),
                    p.getLongitude(),
                    p.getTitle());

            webView.getEngine().executeScript(script);

            // There things can be simplified a lot, but I was asked to illustrate
            // closures so I complicated a little bit this script.
            script = String.format(Locale.US,
           "google.maps.event.addListener(marker, 'click', "
                   + "function(m) {  return function() { "
                                    + "bridge.select(m.placenumber)}"
                               + "}(marker));");

            webView.getEngine().executeScript(script);
        }

        placesListView.getSelectionModel()
                      .selectedItemProperty()
                      .addListener(new ChangeListener<Place>() {

            @Override
            public void changed(ObservableValue<? extends Place> ov, Place oldPlace, Place newPlace) {
                if (newPlace != null) {
                    String script = String.format(Locale.US,
                            "map.panTo(new google.maps.LatLng(%f,%f));",
                            newPlace.getLatitude(),
                            newPlace.getLongitude());

                    webView.getEngine().executeScript(script);
                }
            }
        });
    }

}
