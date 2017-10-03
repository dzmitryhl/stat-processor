package hello.service;

import hello.dao.ForecastDAO;
import hello.dto.request.ForecastRequestDTO;
import hello.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForecastServiceImpl implements ForecastService {

    @Autowired
    private MatchService matchService;

    @Autowired
    private ForecastDAO forecastDAO;

    @Override
    public List<Long> getMatchIdsByPeriod(Long periodId) {
        return forecastDAO.getMatchIdsByPeriod(periodId);
    }

    @Override
    public TotalResult applyStrategy(ForecastRequestDTO forecastRequestDTO) {

        double total = 10;
        double betSum = 10;
        int betNumber = 0;

        TotalResult totalResult = new TotalResult();

        double intervalTotal = 10;
        int intervalBetNumber = 0;

        Interval interval = forecastRequestDTO.getInterval();

        List<Long> ids;
        if (interval != null && interval.getStartDate() != null && interval.getEndDate() != null) {
            ids = matchService.getMatchIds(interval.getStartDate(), interval.getEndDate());
        } else if (forecastRequestDTO.getLoadNRandom() != 0) {
            ids = matchService.getRandomMatchIds(forecastRequestDTO.getLoadNRandom());
        } else {
            ids = matchService.getAllMatchIds();
        }

        List<Forecast> forecastsHistory = new ArrayList<>();
        StrategyType strategyType = forecastRequestDTO.getStrategyType();

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

                    ResultExpectation resultExpectation;

                    if (strategyType == StrategyType.FAVORITE_SCORED) {

                        if (row.getNextGoal1Coef() > row.getNextGoal2Coef()) {
                            resultExpectation = ResultExpectation.TEAM_2_SCORED;
                            favoriteCoefficient = row.getNextGoal2Coef();
                            outsiderCoefficient = row.getNextGoal1Coef();
                            favoriteScore = row.getScore2();
                            outsiderScore = row.getScore1();
                        } else {
                            resultExpectation = ResultExpectation.TEAM_1_SCORED;
                            favoriteCoefficient = row.getNextGoal1Coef();
                            outsiderCoefficient = row.getNextGoal2Coef();
                            favoriteScore = row.getScore1();
                            outsiderScore = row.getScore2();
                        }

                        boolean satisfyConditions;
                        satisfyConditions = forecastRequestDTO.getFavoriteCoefficientMin() == null || favoriteCoefficient > forecastRequestDTO.getFavoriteCoefficientMin();
                        satisfyConditions = satisfyConditions && (forecastRequestDTO.getTimeMin() == null || scoreTime > forecastRequestDTO.getTimeMin());
                        satisfyConditions = satisfyConditions && (forecastRequestDTO.getTimeMax() == null || scoreTime < forecastRequestDTO.getTimeMax());
                        satisfyConditions = satisfyConditions && (forecastRequestDTO.getFavoriteScoreMin() == null || favoriteScore == forecastRequestDTO.getFavoriteScoreMin());
                        satisfyConditions = satisfyConditions && (forecastRequestDTO.getOutsiderScoreMin() == null || outsiderScore == forecastRequestDTO.getOutsiderScoreMin());

                        if (satisfyConditions) {
                            forecast = new Forecast(match, scoreTime, resultExpectation, favoriteCoefficient, betSum);
                            intervalTotal -= betSum;
                            intervalBetNumber++;
                        }

                    } else if (strategyType == StrategyType.OUTSIDER_SCORED) {
                        if (row.getNextGoal1Coef() > row.getNextGoal2Coef()) {
                            resultExpectation = ResultExpectation.TEAM_1_SCORED;
                            favoriteCoefficient = row.getNextGoal2Coef();
                            outsiderCoefficient = row.getNextGoal1Coef();
                            favoriteScore = row.getScore2();
                            outsiderScore = row.getScore1();
                        } else {
                            resultExpectation = ResultExpectation.TEAM_2_SCORED;
                            favoriteCoefficient = row.getNextGoal1Coef();
                            outsiderCoefficient = row.getNextGoal2Coef();
                            favoriteScore = row.getScore1();
                            outsiderScore = row.getScore2();
                        }
                    } else {
                        resultExpectation = ResultExpectation.TEAM_X_SCORED;
                        favoriteCoefficient = row.getNextGoal1Coef() > row.getNextGoal2Coef() ? row.getNextGoal2Coef() : row.getNextGoal1Coef();
                        outsiderCoefficient = row.getNextGoal1Coef() > row.getNextGoal2Coef() ? row.getNextGoal1Coef() : row.getNextGoal2Coef();
                        favoriteScore = row.getNextGoal1Coef() > row.getNextGoal2Coef() ? row.getScore2() : row.getScore1();
                        outsiderScore = row.getNextGoal1Coef() > row.getNextGoal2Coef() ? row.getScore1() : row.getScore2();
                    }

                } else {
                    double increment = forecast.check(row.getScoreTime());

                    intervalTotal += increment;

                    if (forecast.isCompleted()) {
                        i = i - 1;
                        Match matchClone = new Match(match);

                        if (!forecastRequestDTO.isShowDetails()) {
                            matchClone.setDetails(null);
                        }

                        forecastsHistory.add(new Forecast(matchClone, forecast.getMinute(), forecast.getResultExpectation(), forecast.getCoefficient(), forecast.getBetSum(), forecast.isCompleted(), forecast.isWinning()));
                        forecast = null;
                    } else if (row.getScoreTime() == 90) {
                        //forecast.setCompleted(true);
                        Match matchClone = new Match(match);
                        if (!forecastRequestDTO.isShowDetails()) {
                            matchClone.setDetails(null);
                        }
                        forecastsHistory.add(new Forecast(matchClone, forecast.getMinute(), forecast.getResultExpectation(), forecast.getCoefficient(), forecast.getBetSum(), forecast.isCompleted(), forecast.isWinning()));
                        forecast = null;
                    }
                }
            }
        }

        IntervalResult intervalResult = new IntervalResult();
        intervalResult.setTotal(intervalTotal);
        intervalResult.setNumberOfBets(intervalBetNumber);

        intervalResult.setForecasts(null);

        if (forecastRequestDTO.isShowHistory()) {
            intervalResult.setForecasts(forecastsHistory);
        }

        totalResult.getPartialResults().add(intervalResult);

        total += intervalTotal;
        betNumber += intervalBetNumber;

        totalResult.setTotal(total);
        totalResult.setNumberOfBets(betNumber);

        return totalResult;
    }
}
