package hello.service;

import hello.dao.ForecastDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForecastServiceImpl implements ForecastService {

    @Autowired
    private ForecastDAO forecastDAO;

    @Override
    public List<Long> getMatchIdsByPeriod(Long periodId) {
        return forecastDAO.getMatchIdsByPeriod(periodId);
    }
}
