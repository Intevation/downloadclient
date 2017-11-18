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

package de.bayern.gdi.utils.settings;

import de.bayern.gdi.utils.Misc;
import de.bayern.gdi.utils.service.ServiceSettings;
import de.bayern.gdi.utils.XML;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Class managing all settings.
 * @author Alexander Woestmann (awoestmann@intevation)
 */

public class GeneralSettings {

    /** Name of the config file. */
    public static final String SETTINGS_FILE =
            "settings.xml";

    private static final String NAME =
            "GeneralSettings";

    private ServiceSettings serviceSettings;

    private ApplicationSettings applicationSettings;

    public GeneralSettings()
            throws SAXException, ParserConfigurationException, IOException {
        this(SETTINGS_FILE);
    }

    /**
     * Constructor.
     * @param filePath Path to the settings xml document
     */
    public GeneralSettings(String filePath)
        throws SAXException, ParserConfigurationException, IOException {
        this(XML.getDocument(getFileStream(filePath)));
    }

    public GeneralSettings(File file)
        throws SAXException, ParserConfigurationException, IOException {
        this(XML.getDocument(file));
    }

    public GeneralSettings(Document doc) throws IOException {
        this.serviceSettings = new ServiceSettings(doc);
        this.applicationSettings = new ApplicationSettings(doc);
    }

    /**
     * Returns service settings manager class.
     * @return service settings
     */
    public ServiceSettings getServiceSettings() {
        return this.serviceSettings;
    }

    /**
     * Returns application settings manager class.
     * @return Application settings
     */
    public ApplicationSettings getApplicationSettings() {
        return this.applicationSettings;
    }

    private static InputStream getFileStream(String fileName) {
        return Misc.getResource(fileName);
    }

    /**
     * Return class name.
     * @return The name
     */
    public static String getName() {
        return NAME;
    }
}
