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

import de.bayern.gdi.gui.Controller;
import de.bayern.gdi.gui.DataBean;
import de.bayern.gdi.gui.MiscItemModel;
import de.bayern.gdi.gui.OutputFormatModel;
import de.bayern.gdi.gui.Validator;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import java.util.function.Consumer;

/**
 * Validates some inputs.
 *
 * @author thomas
 */
public class InputValidator {

    /**
     * The controller which is referred.
     */
    private Controller controller;

    /**
     * Constructor.
     * @param c controller to refer to
     */
    public InputValidator(Controller c) {
        this.controller = c;
    }

    /**
     * Validation function.
     * @return true or false
     */
    public boolean validate() {
        final StringBuilder failed = new StringBuilder();

        Consumer<String> fail = s -> {
            if (failed.length() != 0) {
                failed.append(", ");
            }
            failed.append(s);
        };

        for (DataBean.Attribute attr : controller.getAttributesOfDataBean()) {
            if (!attr.getType().isEmpty()
                    && !Validator.isValid(attr.getType(), attr.getValue())) {
                fail.accept(attr.getName());
            }
        }

        if (controller.isDownloadConfigSet()) {
            if (isMiscItemModel()) {
                fail.accept(I18n.format("gui.dataset"));
            }

            if (controller.isAtomContainerVisible()
                    && controller.isAtomVariationChooserAMiscItemModel()) {
                fail.accept(I18n.format("gui.variants"));
            }

            if (controller.isReferenceSystemChooserVisible()
                    && controller.isReferenceSystemChooserNotAvailable()) {
                fail.accept(I18n.format("gui.reference-system"));
            }

            if (controller.isBasicWFSContainerVisible()
                    && controller.isDataFormatChooserVisible()
                    && controller.isDataFormatChooserNotAvailable()) {
                fail.accept(I18n.format("gui.data-format"));
            }

            if (controller.isSimpleWFSContainerVisible()) {
                ObservableList<Node> children
                    = controller.getSimpleWFSContainerChildren();
                for (Node node : children) {
                    if (node instanceof HBox) {
                        HBox hb = (HBox) node;
                        Node n2 = hb.getChildren().get(1);
                        if (n2 instanceof ComboBox) {
                            ComboBox<OutputFormatModel> cb
                                = (ComboBox<OutputFormatModel>) n2;
                            if (!cb.getValue().isAvailable()) {
                                fail.accept(I18n.format("gui.data-format"));
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (failed.length() == 0) {
            return true;
        }
        controller.setStatusTextUI(
            I18n.format("status.validation-fail", failed.toString()));
        return false;
    }

    /**
     * Is it a misc item model?
     * @return true, if so
     */
    private boolean isMiscItemModel() {
        return controller.serviceTypeChooser.isVisible()
                && controller.serviceTypeChooser
                       .getValue() instanceof MiscItemModel;
    }
}
