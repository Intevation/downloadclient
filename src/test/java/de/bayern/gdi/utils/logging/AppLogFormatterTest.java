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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.util.logging.LogRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests the log format.
 *
 * @author thomas
 */
@RunWith(MockitoJUnitRunner.class)
public class AppLogFormatterTest {

    /**
     * A mocking entry.
     */
    @Mock
    private LogRecord record;

    /**
     * Testing the format.
     *
     * @throws Exception an exception
     */
    @Test
    public void format() throws Exception {
        String message = "BANANARAMA";
        AppLogFormatter formatter = new AppLogFormatter();
        when(record.getMessage()).thenReturn(message);
        String result = formatter.format(record);
        assertTrue(result.contains(message));
    }

}
