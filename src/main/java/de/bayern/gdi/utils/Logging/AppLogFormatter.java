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

package de.bayern.gdi.utils.Logging;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Format definition of Applicationlogs.
 *
 * @author thomas
 */
public class AppLogFormatter extends Formatter {
    /**
     * Formats log record.
     *
     * @return Formatted log entry
     */
    @Override
    public String format(LogRecord record) {
        LocalDateTime time = Instant.ofEpochMilli(record.getMillis())
                                 .atZone(ZoneId.systemDefault())
                                 .toLocalDateTime();
        return time.format(DateTimeFormatter
                               .ofPattern("E, dd.MM.yyyy - kk:mm:ss"))
                   + " "
                   + record.getMessage()
                   + System.lineSeparator();
    }
}
