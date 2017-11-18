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

package de.bayern.gdi.utils.logging;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;

/**
 * Trashbin for logging releated stuff.
 *
 * @author thomas
 */
public class AppLog {

    /**
     * Magical number of bytes to log.
     */
    private static final int MAX_APP_LOG_BYTES = 8096;

    /**
     * Creates a Logfilehandler for the App.
     *
     * @return a Filehandler
     * @throws IOException just in case
     */
    public FileHandler createAppLogFileHandler() throws IOException {
        //Open file in append mode
        File logPath = new File(System.getProperty("java.io.tmpdir")
                                    + "/downloadclient");
        logPath.mkdirs();

        String path = logPath.getAbsolutePath() + "/downloadclient_app_log.txt";
        FileHandler appLogFileHandler = new FileHandler(path,
                                                           MAX_APP_LOG_BYTES,
                                                           1,
                                                           true);
        AppLogFormatter appLogFormatter = new AppLogFormatter();
        appLogFileHandler.setFormatter(appLogFormatter);
        return appLogFileHandler;
    }

}
