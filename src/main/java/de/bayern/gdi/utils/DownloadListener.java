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

    /**
     * Message.
     */
    private String message;
    /**
     * The main JavaFX controller.
     */
    private final Controller controller;

    /**
     * Constructor.
     * @param cnt referenced controller
     */
    public DownloadListener(final Controller cnt) {
        this.controller = cnt;
    }

    /**
     * Returns sent message.
     * @return the message
     */
    private synchronized String getMessage() {
        return this.message;
    }

    /**
     * Sets the message.
     * @param msg the message
     */
    private synchronized void setMessage(final String msg) {
        this.message = msg;
    }

    /**
     * The run method.
     */
    @Override
    public void run() {
        controller.setStatusTextUI(getMessage());
    }

    /**
     * Received Exception.
     * @param pe The event. pe.getException() returns the
     */
    @Override
    public void receivedException(final ProcessorEvent pe) {
        setMessage(
            I18n.format(
                "status.error",
                pe.getException().getMessage()));
        Platform.runLater(this);
    }

    /**
     * Reiceived Message.
     * @param pe The event. pe.getMessage() returns the
     */
    @Override
    public void receivedMessage(final ProcessorEvent pe) {
        setMessage(pe.getMessage());
        Platform.runLater(this);
    }
}
