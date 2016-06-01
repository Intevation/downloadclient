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
package de.bayern.gdi.services;

import java.io.IOException;
import java.net.URL;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.bayern.gdi.utils.NamespaceContextMap;
import de.bayern.gdi.utils.XML;

/** Extract meta data from a WFS. */
public class WFSMetaExtractor {

    private static final String XPATH_TITLE
        = "//ows:ServiceIdentification/ows:Title/text()";

    private static final String XPATH_ABSTRACT
        = "//ows:ServiceIdentification/ows:Abstract/text()";

    private static final String XPATH_OPERATIONS
        = "//ows:OperationsMetadata/ows:Operation";

    private WFSMetaExtractor() {
    }

    /**
     * Extracts meta information from a given capabilities path.
     * @param capURLString The URL of the capabilities document.
     * @return The extracted meta data.
     * @throws IOException If something went wrong.
     */
    public static WFSMeta parse(String capURLString) throws IOException {
        URL capURL = new URL(capURLString);
        Document capDoc = XML.getDocument(capURL);
        if (capDoc == null) {
            throw new IOException("Cannot load capabilities document.");
        }
        NamespaceContextMap nc = new NamespaceContextMap(
                "ows", "http://www.opengis.net/ows/1.1");
        WFSMeta meta = new WFSMeta();
        meta.title = XML.xpathString(capDoc, XPATH_TITLE, nc);
        meta.abstractDescription
            = XML.xpathString(capDoc, XPATH_ABSTRACT, nc);

        NodeList nl = (NodeList)XML.xpath(
            capDoc, XPATH_OPERATIONS, XPathConstants.NODESET, nc);

        for (int i = 0, n = nl.getLength(); i < n; i++) {
            WFSMeta.Operation operation = new WFSMeta.Operation();
            Element node = (Element)nl.item(i);
            operation.name = node.getAttribute("name");
            meta.operations.add(operation);
        }
        return meta;
    }
}
