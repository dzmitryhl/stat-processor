package hello.dao;

import hello.dto.request.ForecastRequestDTO;
import hello.model.IntervalResult;

import java.util.List;

public interface ForecastDAO {
    List<Long> getMatchIdsByPeriod(Long periodId);
    IntervalResult applyStrategy(ForecastRequestDTO forecastRequestDTO);
}
