package skillbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@SpringBootApplication
public class Main {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Marker EXCEPTION = MarkerManager.getMarker("EXCEPTION");
    public static void main(String[] args) {
        try {
            SpringApplication.run(Main.class, args);
        } catch(Exception ex) {
            LOGGER.info(EXCEPTION, "main", ex);
        }
    }
}
