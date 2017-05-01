package hello;

import hello.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.xml.soap.Detail;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class TestController {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static String GET_MATCH_IDS_BY_PERIOD = "select id from matches where match_date > (select \"from\" from periods where id = :period_id) and  match_date <= (select \"to\" from periods where id = :period_id)";
    private static String GET_MATCH_BY_ID = "select id, home_team, away_team, sport_name, location, championship, initially_completed from matches where id = :id";
    private static String GET_DETAILS_BY_MATCH_ID = "select id, score_time, score1, score2, next_goal_1_coef, next_goal_x_coef, next_goal_2_coef, team_1_scored, team_2_scored from details where id = :id";
    private static String GET_PERIODS = "select id, name, \"from\", \"to\" from periods";


/*    @RequestMapping("/splitName")
    public void splitNames() {
        List<Match> matches = this.namedParameterJdbcTemplate.query(
                "select id, name from matches",
                (rs, rowNum) -> {
                    Match match = new Match();
                    match.setId(rs.getLong(1));
                    match.setName(rs.getString(2));
                    return match;
                });

        for (Match match : matches) {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();

            String[] parts = match.getName().split(" - ");

            namedParameters.addValue("id", match.getId());
            namedParameters.addValue("home_team", parts[0]);
            namedParameters.addValue("away_team", parts[1]);

            this.namedParameterJdbcTemplate.update(
                    "update matches set home_team = :home_team, away_team = :away_team where id = :id",
                    namedParameters);
        }
    }*/


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/periods")
    public List<Period> getPeriods() {
        return this.namedParameterJdbcTemplate.query(
                GET_PERIODS,
                (rs, rowNum) -> {
                    Period period = new Period();
                    period.setId(rs.getLong(1));
                    period.setName(rs.getString(2));
                    period.setFrom(rs.getTimestamp(3));
                    period.setTo(rs.getTimestamp(4));
                    return period;
                });
    }










/*    @RequestMapping("/")
    public String getTest() {
        List<Long> ids = this.jdbcTemplate.query(
                GET_UNIQUE_IDS,
                (rs, rowNum) -> rs.getLong(1));

        double total = 10;
        double betSum = 10;
        int betNumber = 0;

        for(Long id : ids) {
            Match match = getMatch(id);

            List<Forecast> forecasts = new ArrayList<>();

            for (int i = 0; i < match.getDetails().size(); i++) {
                Row row = match.getDetails().get(i);

                if (forecasts.size() == 0) {

                    int scoreTime = row.getScoreTime();

                    double favoriteCoefficient;
                    double outsiderCoefficient;

                    int favoriteScore;
                    int outsiderScore;

//                  + 204

                    ResultExpectation favoriteScored;
                    ResultExpectation outsiderScored;

                    if (row.getNextGoal1Coef() > row.getNextGoal2Coef()) {
                        favoriteScored = ResultExpectation.TEAM_2_SCORED;
                        outsiderScored = ResultExpectation.TEAM_1_SCORED;

                        favoriteCoefficient = row.getNextGoal2Coef();
                        outsiderCoefficient = row.getNextGoal1Coef();
                        favoriteScore = row.getScore2();
                        outsiderScore = row.getScore1();
                    } else {
                        favoriteScored = ResultExpectation.TEAM_1_SCORED;
                        outsiderScored = ResultExpectation.TEAM_2_SCORED;
                        favoriteCoefficient = row.getNextGoal1Coef();
                        outsiderCoefficient = row.getNextGoal2Coef();
                        favoriteScore = row.getScore1();
                        outsiderScore = row.getScore2();
                    }

                    if ((favoriteCoefficient * outsiderCoefficient) / (favoriteCoefficient + outsiderCoefficient) > 2 && scoreTime < 65) {


                        double favoriteBetSum = betSum / favoriteCoefficient;
                        double outsiderBetSum = betSum - favoriteBetSum;


//                        Forecast forecast1 = new Forecast(scoreTime, favoriteScored, favoriteCoefficient, betSum - betSum * favoriteCoefficient / (favoriteCoefficient + outsiderCoefficient));
//                        Forecast forecast2 = new Forecast(scoreTime, outsiderScored, outsiderCoefficient, betSum * favoriteCoefficient / (favoriteCoefficient + outsiderCoefficient));

                        Forecast forecast1 = new Forecast(scoreTime, favoriteScored, favoriteCoefficient, favoriteBetSum);
                        Forecast forecast2 = new Forecast(scoreTime, outsiderScored, outsiderCoefficient, outsiderBetSum);

                        forecasts.add(forecast1);
                        forecasts.add(forecast2);
                        total -= betSum;
                        betNumber++;
                    }
                } else {

                    boolean forecastsCompleted = false;

                    for (Forecast forecast : forecasts) {
                        double increment = match.checkForecast(forecast, row.getScoreTime());
                        total += increment;

                        if (forecast.isCompleted()) {
                            forecastsCompleted = true;
                        }
                    }

                    if (forecastsCompleted) {
                        if (row.getScoreTime() != 90) {
                            i = i - 1;
                        }
                        forecasts = new ArrayList<>();
                    }
                }
            }
        }

        return "Total: " + total + "; Number of bets: " + betNumber + "; Profit: " + ((total - betSum) / (betNumber * betSum)) + "%";
    }*/


















/*    @RequestMapping("/")
    public IntervalResult getTest() {
        List<Long> ids = this.jdbcTemplate.query(
                GET_UNIQUE_IDS,
                (rs, rowNum) -> rs.getLong(1));

        double total = 10;
        double betSum = 10;
        int betNumber = 0;

        List<Forecast> forecastsHistory = new ArrayList<>();

        for(Long id : ids) {
            Match match = getMatch(id);
            Forecast forecast = null;

            double coefficient1 = match.getDetails().get(0).getNextGoal1Coef();
            double coefficient2 = match.getDetails().get(0).getNextGoal2Coef();

            boolean team1Favorite = false;
            boolean team2Favorite = false;

            if (coefficient2 / coefficient1 > 1.5) {
                team1Favorite = true;
            } else if (coefficient1 / coefficient2 > 1.5) {
                team2Favorite = true;
            }

            for (int i = 0; i < match.getDetails().size(); i++) {

                Row row = match.getDetails().get(i);

                if (forecast == null) {

                    int scoreTime = row.getScoreTime();

                    double favoriteCoefficient = 0;
                    double outsiderCoefficient = 0;
                    double noOneCoefficient = 0;

                    int favoriteScore = 0;
                    int outsiderScore = 0;

                    ResultExpectation favoriteScored = ResultExpectation.TEAM_X_SCORED;
                    ResultExpectation outsiderScored = ResultExpectation.TEAM_X_SCORED;
                    ResultExpectation noOneScored = ResultExpectation.TEAM_X_SCORED;

                    if (team1Favorite) {
                        favoriteScored = ResultExpectation.TEAM_1_SCORED;
                        outsiderScored = ResultExpectation.TEAM_2_SCORED;
                        favoriteCoefficient = row.getNextGoal1Coef();
                        outsiderCoefficient = row.getNextGoal2Coef();
                        noOneCoefficient = row.getNextGoalXCoef();
                        favoriteScore = row.getScore1();
                        outsiderScore = row.getScore2();
                    } else if (team2Favorite) {
                        favoriteScored = ResultExpectation.TEAM_2_SCORED;
                        outsiderScored = ResultExpectation.TEAM_1_SCORED;
                        favoriteCoefficient = row.getNextGoal2Coef();
                        outsiderCoefficient = row.getNextGoal1Coef();
                        noOneCoefficient = row.getNextGoalXCoef();
                        favoriteScore = row.getScore2();
                        outsiderScore = row.getScore1();
                    }

                    if (team1Favorite || team2Favorite) {
                        if (favoriteScore + outsiderScore > 4 && noOneCoefficient > 1.2 && noOneCoefficient < 4) {
                            forecast = new Forecast(match.getId(), scoreTime, noOneScored, noOneCoefficient, betSum);
                            total -= betSum;
                            betNumber++;
                        }
                    }

                } else {
                    double increment = match.checkForecast(forecast, row.getScoreTime());
                    total += increment;

                    if (forecast.isCompleted()) {
                        i = i - 1;
                        forecastsHistory.add(new Forecast(forecast.getEventId(), forecast.getMinute(), forecast.getResultExpectation(), forecast.getCoefficient(), forecast.getBetSum(), forecast.isCompleted(), forecast.isWinning()));
                        forecast = null;
                    } else if (row.getScoreTime() == 90) {
                        forecastsHistory.add(new Forecast(forecast.getEventId(), forecast.getMinute(), forecast.getResultExpectation(), forecast.getCoefficient(), forecast.getBetSum(), forecast.isCompleted(), forecast.isWinning()));
                        forecast = null;
                    }
                }
            }
        }

        IntervalResult resultInfo = new IntervalResult();
        resultInfo.setTotal(total);
        resultInfo.setNumberOfBets(betNumber);
        resultInfo.setForecasts(forecastsHistory);

        return resultInfo;
    }*/







    private Match getMatch(long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        Match match = this.namedParameterJdbcTemplate.queryForObject(
                GET_MATCH_BY_ID,
                namedParameters,
                (rs, rowNum) -> {
                    Match m = new Match();
                    m.setId(rs.getLong(1));
                    m.setSportName(rs.getString(2));
                    m.setHomeTeam(rs.getString(3));
                    m.setAwayTeam(rs.getString(4));
                    m.setLocation(rs.getString(5));
                    m.setChampionship(rs.getString(6));
                    m.setInitiallyCompleted(rs.getBoolean(7));
                    return m;
                });
        match.setDetails(getDetails(id));

        return match;
    }

    private List<StatisticsRowHolder> getDetails(long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        return this.namedParameterJdbcTemplate.query(
                GET_DETAILS_BY_MATCH_ID,
                namedParameters,
                (rs, rowNum) -> {
                    StatisticsRowHolder row = new StatisticsRowHolder();

                    row.setId(rs.getLong(1));
                    row.setScoreTime(rs.getInt(2));
                    row.setScore1(rs.getInt(3));
                    row.setScore2(rs.getInt(4));
                    row.setNextGoal1Coef(rs.getDouble(5));
                    row.setNextGoalXCoef(rs.getDouble(6));
                    row.setNextGoal2Coef(rs.getDouble(7));
                    row.setTeam1Scored(rs.getBoolean(8));
                    row.setTeam2Scored(rs.getBoolean(9));

                    return row;
                });
    }
}