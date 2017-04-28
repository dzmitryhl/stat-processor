package hello.dao;

import java.util.List;

public interface ForecastDAO {
    List<Long> getMatchIdsByPeriod(Long periodId);
}
