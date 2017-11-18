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
import de.bayern.gdi.processor.ProcessorEvent;
import de.bayern.gdi.processor.ProcessorListener;
import javafx.application.Platform;

/**
 * Keeps track of download progression and errors.
 *
 * @author thomas
 */
public class DownloadListener implements ProcessorListener, Runnable {

    private String message;
    private Controller controller;

    public DownloadListener(Controller controller) {
        this.controller = controller;
    }

    private synchronized String getMessage() {
        return this.message;
    }

    private synchronized void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        controller.setStatusTextUI(getMessage());
    }

    @Override
    public void receivedException(ProcessorEvent pe) {
        setMessage(
            I18n.format(
                "status.error",
                pe.getException().getMessage()));
        Platform.runLater(this);
    }

    @Override
    public void receivedMessage(ProcessorEvent pe) {
        setMessage(pe.getMessage());
        Platform.runLater(this);
    }
}
