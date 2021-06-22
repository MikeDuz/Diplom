package skillbox.util;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class DateConvertor {

    public static long getTimestamp(LocalDateTime date) {
        if (date == null) {
            return 0;
        }

        return date.toEpochSecond(OffsetDateTime.now().getOffset());
    }
}
