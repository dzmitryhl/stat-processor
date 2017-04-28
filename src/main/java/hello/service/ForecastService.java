package hello.service;

import java.util.List;

public interface ForecastService {
    List<Long> getMatchIdsByPeriod(Long periodId);
}
