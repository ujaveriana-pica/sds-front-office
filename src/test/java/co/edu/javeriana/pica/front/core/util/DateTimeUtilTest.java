package co.edu.javeriana.pica.front.core.util;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DateTimeUtilTest {
    @Test
    public void nowNotNull() {
        Date now = DateTimeUtil.now();
        assertNotNull(now);
    }
}
