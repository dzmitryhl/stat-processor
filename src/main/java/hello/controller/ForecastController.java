package hello.controller;

import hello.model.*;
import hello.service.ForecastService;
import hello.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ForecastController {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    ForecastService forecastService;

    @Autowired
    MatchService matchService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/loadData", method = RequestMethod.POST)
    public ResultInfo loadData(@RequestBody FilterDTO filterDTO) {

        List<Long> ids = forecastService.getMatchIdsByPeriod(filterDTO.getPeriodId());

        double total = 10;
        double betSum = 10;
        int betNumber = 0;

        List<Forecast> forecastsHistory = new ArrayList<>();

        for(Long id : ids) {
            Match match = matchService.getMatchById(id);
            match.setDetails(matchService.getDetails(match.getId()));

            Forecast forecast = null;

            for (int i = 0; i < match.getDetails().size(); i++) {
                StatisticsRowHolder row = match.getDetails().get(i);
                if (forecast == null) {

                    int scoreTime = row.getScoreTime();

                    double favoriteCoefficient;
                    double outsiderCoefficient;

                    int favoriteScore;
                    int outsiderScore;

//                  + 204

                    Result favoriteScored;

                    if (row.getNextGoal1Coef() > row.getNextGoal2Coef()) {
                        favoriteScored = Result.TEAM_2_SCORED;
                        favoriteCoefficient = row.getNextGoal2Coef();
                        outsiderCoefficient = row.getNextGoal1Coef();
                        favoriteScore = row.getScore2();
                        outsiderScore = row.getScore1();
                    } else {
                        favoriteScored = Result.TEAM_1_SCORED;
                        favoriteCoefficient = row.getNextGoal1Coef();
                        outsiderCoefficient = row.getNextGoal2Coef();
                        favoriteScore = row.getScore1();
                        outsiderScore = row.getScore2();
                    }

                    if (favoriteCoefficient > 2.3 && scoreTime > 45 && scoreTime < 50 && favoriteScore == 0 && outsiderScore == 0) {
                        forecast = new Forecast(match.getId(), scoreTime, favoriteScored, favoriteCoefficient, betSum);
                        total -= betSum;
                        betNumber++;
                    }
                } else {
                    double increment = match.checkForecast(forecast, row.getScoreTime());
                    total += increment;

                    if (forecast.isCompleted()) {
                        i = i - 1;
                        forecastsHistory.add(new Forecast(match.getLocation(), match.getChampionship(), row.getScore1(), row.getScore2(), forecast.getEventId(), forecast.getMinute(), forecast.getResult(), forecast.getCoefficient(), forecast.getBetSum(), forecast.isCompleted(), forecast.isWinning()));
                        forecast = null;
                    } else if (row.getScoreTime() == 90) {
                        //forecast.setCompleted(true);
                        forecastsHistory.add(new Forecast(match.getLocation(), match.getChampionship(), row.getScore1(), row.getScore2(), forecast.getEventId(), forecast.getMinute(), forecast.getResult(), forecast.getCoefficient(), forecast.getBetSum(), forecast.isCompleted(), forecast.isWinning()));
                        forecast = null;
                    }
                }
            }
        }


        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setTotal(total);
        resultInfo.setNumberOfBets(betNumber);
        resultInfo.setForecasts(forecastsHistory);

        return resultInfo;
    }
}
