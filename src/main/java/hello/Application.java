package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class Application {

    @Value("${app.start.time}")
    private static int startingMinute;

    public static AtomicInteger counter = new AtomicInteger(startingMinute);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        TimerTask task = new ChangeCurrentMinuteTask(counter);
        Timer timer = new Timer();
        timer.schedule(task, 0, 60000);
    }
}