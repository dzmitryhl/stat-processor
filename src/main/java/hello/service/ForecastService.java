package hello.service;

import hello.dto.request.ForecastRequestDTO;
import hello.model.TotalResult;

import java.util.List;

public interface ForecastService {
    List<Long> getMatchIdsByPeriod(Long periodId);
    TotalResult applyStrategy(ForecastRequestDTO forecastRequestDTO);
}
