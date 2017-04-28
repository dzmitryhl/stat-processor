package hello;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class ChangeCurrentMinuteTask extends TimerTask
{
    private final AtomicInteger counter;

    ChangeCurrentMinuteTask(AtomicInteger counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        System.out.println("Current minute: " + counter.incrementAndGet());
    }
}
