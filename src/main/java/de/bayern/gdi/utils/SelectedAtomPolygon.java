/*
 * DownloadClient Geodateninfrastruktur Bayern
 *
 * (c) 2016 GSt. GDI-BY (gdi.bayern.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.bayern.gdi.utils;

import de.bayern.gdi.gui.AtomItemModel;
import de.bayern.gdi.gui.Controller;
import de.bayern.gdi.gui.ItemModel;
import de.bayern.gdi.gui.PolygonClickedEvent;
import de.bayern.gdi.gui.WMSMapSwing;
import de.bayern.gdi.services.Atom;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * Handles the Action, when a polygon is selected.
 *
 * @author thomas
 */
public class SelectedAtomPolygon implements
    EventHandler<Event> {
    /**
     * The referenced controller.
     */
    private Controller controller;

    /**
     * Constructor.
     *
     * @param cont controller to refer to.
     */
    public SelectedAtomPolygon(final Controller cont) {
        this.controller = cont;
    }

    /**
     * Handle event.
     *
     * @param event event
     */
    @Override
    public void handle(final Event event) {
        if (isPolygonClicked(event)) {

            PolygonClickedEvent pce = (PolygonClickedEvent) event;
            WMSMapSwing.PolygonInfos polygonInfos =
                pce.getPolygonInfos();
            String polygonName = polygonInfos.getName();
            String polygonID = polygonInfos.getID();

            if (polygonName != null && polygonID != null) {
                if (polygonName.equals("#@#")) {
                    controller.setStatusTextUI(I18n.format(
                        "status.polygon-intersect",
                        polygonID));
                    return;
                }

                ObservableList<ItemModel> items =
                    controller.serviceTypeChooser.getItems();
                int i;
                for (i = 0; i < items.size(); i++) {
                    AtomItemModel item = (AtomItemModel) items.get(i);
                    Atom.Item aitem = (Atom.Item) item.getItem();
                    if (aitem.getID().equals(polygonID)) {
                        break;
                    }
                }
                Atom.Item oldItem =
                    (Atom.Item) controller.serviceTypeChooser
                                    .getSelectionModel()
                                    .getSelectedItem().getItem();
                if (i < items.size()
                        && !oldItem.getID().equals(polygonID)) {
                    controller.serviceTypeChooser.setValue(items.get(i));
                    ItemModel value = controller.serviceTypeChooser.getValue();
                    controller.chooseType(value);
                }
            }
        }
    }

    /**
     * Determines whether the event was from a clicked polygon.
     *
     * @param event the clicked event
     * @return true or false
     */
    private boolean isPolygonClicked(final Event event) {
        return controller.mapAtom != null
                   && event instanceof PolygonClickedEvent;
    }
}
