package hello.dao;

import hello.dto.ResultCandidateDto;
import hello.model.Match;
import hello.model.Period;
import hello.model.RowHolder;
import hello.model.StatisticsRowHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MatchDAOImpl implements MatchDAO {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private String GET_MATCH_IDS = "select m.id from matches m left join details d on d.id = m.id where d.score_time = 90 and coalesce(planned_kickoff_date, actual_kickoff_date) > :startDate and coalesce(planned_kickoff_date, actual_kickoff_date) <= :endDate";
    private String GET_MATCH_BY_ID = "select id, sport_name, location, championship, home_team, away_team, planned_kickoff_date, actual_kickoff_date, initially_completed, score_confirmed from matches where id = :id";
    private String GET_DETAIL_BY_ID = "select id, score_time, score1, score2, next_goal_1_coef, next_goal_x_coef, next_goal_2_coef, team_1_scored, team_2_scored, reconstructed from details where id = :id and score_time = :score_time";
    private String GET_DETAILS_BY_ID = "select id, score_time, score1, score2, next_goal_1_coef, next_goal_x_coef, next_goal_2_coef, team_1_scored, team_2_scored, reconstructed from details where id = :id";
    private static String GET_RESULT_CANDIDATES = "select r.championship, r.home_team, r.away_team, r.result_score from matches m join results r on m.planned_kickoff_date = (r.date - interval '2 hour') and m.location = r.location where m.id = :id";


    private static String GET_ROWS_BY_ID = "select id, sport_name, home_team, away_team, score_time, score_details, location, championship, next_goal_1_coef, next_goal_x_coef, next_goal_2_coef, timestamp_current, kickoff_date, reconstructed from archive where id = :id";

    private static String GET_LAST_MATCH_DATE = "select coalesce((select max(planned_kickoff_date) from matches), '2016-12-15 00:00:00')";


    private static String GET_ARCHIVE_IDS =
            "select s.id, s.rows_collected, s.kickoff_date\n" +
            "from (\n" +
            "    select id, count(id) rows_collected, kickoff_date \n" +
            "    from archive \n" +
            "    group by id, kickoff_date \n" +
            ") s\n" +
            "where s.kickoff_date > :kickoff_date and s.rows_collected between :min_rows and :max_rows";


    private static String SAVE_ARCHIVE_ENTRY = "insert into archive (id, sport_name, home_team, away_team, score_time, score_details, location, championship, next_goal_1_coef, next_goal_x_coef, next_goal_2_coef, timestamp_current, kickoff_date, reconstructed) values (:id, :sport_name, :home_team, :away_team, :score_time, :score_details, :location, :championship, :next_goal_1_coef, :next_goal_x_coef, :next_goal_2_coef, :timestamp_current, :kickoff_date, :reconstructed)";


    private static String SAVE_MATCH = "insert into matches (id, sport_name, location, championship, home_team, away_team, planned_kickoff_date, actual_kickoff_date, initially_completed, score_confirmed) values (:id, :sport_name, :location, :championship, :home_team, :away_team, :planned_kickoff_date, :actual_kickoff_date, :initially_completed, :score_confirmed)";
    private static String SAVE_DETAIL = "insert into details (id, score_time, score1, score2, next_goal_1_coef, next_goal_x_coef, next_goal_2_coef, team_1_scored, team_2_scored, reconstructed) values (:id, :score_time, :score1, :score2, :next_goal_1_coef, :next_goal_x_coef, :next_goal_2_coef, :team_1_scored, :team_2_scored, :reconstructed)";
    private static String SAVE_PERIOD = "insert into periods (name, \"from\", \"to\") values (:name, :from, :to)";


    @Override
    public List<Long> getMatchIds(Timestamp startDate, Timestamp endDate, boolean uncompletedOnly) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("startDate", startDate);
        namedParameters.addValue("endDate", endDate);

        return this.namedParameterJdbcTemplate.query(
                uncompletedOnly ? GET_MATCH_IDS + " and d.reconstructed = true" : GET_MATCH_IDS,
                namedParameters,
                (rs, rowNum) -> rs.getLong(1));
    }

    @Override
    public Match getMatchById(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        return this.namedParameterJdbcTemplate.queryForObject(
                GET_MATCH_BY_ID,
                namedParameters,
                (rs, rowNum) -> {
                    Match m = new Match();
                    m.setId(rs.getLong(1));
                    m.setSportName(rs.getString(2));
                    m.setLocation(rs.getString(3));
                    m.setChampionship(rs.getString(4));
                    m.setHomeTeam(rs.getString(5));
                    m.setAwayTeam(rs.getString(6));
                    m.setPlannedKickoffDate(rs.getTimestamp(7));
                    m.setActualKickoffDate(rs.getTimestamp(8));
                    m.setInitiallyCompleted(rs.getBoolean(9));
                    m.setInitiallyCompleted(rs.getBoolean(10));
                    return m;
                });
    }

    @Override
    public StatisticsRowHolder getDetail(Long matchId, int scoreTime) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", matchId);
        namedParameters.addValue("score_time", scoreTime);

        return this.namedParameterJdbcTemplate.queryForObject(
                GET_DETAIL_BY_ID,
                namedParameters,
                (rs, rowNum) -> {
                    StatisticsRowHolder s = new StatisticsRowHolder();
                    s.setId(rs.getLong(1));
                    s.setScoreTime(rs.getInt(2));
                    s.setScore1(rs.getInt(3));
                    s.setScore2(rs.getInt(4));
                    s.setNextGoal1Coef(rs.getDouble(5));
                    s.setNextGoalXCoef(rs.getDouble(6));
                    s.setNextGoal2Coef(rs.getDouble(7));
                    s.setTeam1Scored(rs.getBoolean(8));
                    s.setTeam2Scored(rs.getBoolean(9));
                    s.setReconstructed(rs.getBoolean(10));
                    return s;
                });
    }

    @Override
    public List<StatisticsRowHolder> getDetails(Long matchId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", matchId);

        return this.namedParameterJdbcTemplate.query(
                GET_DETAILS_BY_ID,
                namedParameters,
                (rs, rowNum) -> {
                    StatisticsRowHolder s = new StatisticsRowHolder();
                    s.setId(rs.getLong(1));
                    s.setScoreTime(rs.getInt(2));
                    s.setScore1(rs.getInt(3));
                    s.setScore2(rs.getInt(4));
                    s.setNextGoal1Coef(rs.getDouble(5));
                    s.setNextGoalXCoef(rs.getDouble(6));
                    s.setNextGoal2Coef(rs.getDouble(7));
                    s.setTeam1Scored(rs.getBoolean(8));
                    s.setTeam2Scored(rs.getBoolean(9));
                    s.setReconstructed(rs.getBoolean(10));
                    return s;
                });
    }

    @Override
    public List<ResultCandidateDto> getResultCandidates(Long matchId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", matchId);

        return this.namedParameterJdbcTemplate.query(
                GET_RESULT_CANDIDATES,
                namedParameters,
                (rs, rowNum) -> {
                    ResultCandidateDto resultCandidateDto = new ResultCandidateDto();
                    resultCandidateDto.setChampionship(rs.getString(1));
                    resultCandidateDto.setHomeTeam(rs.getString(2));
                    resultCandidateDto.setAwayTeam(rs.getString(3));
                    resultCandidateDto.setResult(rs.getString(4));
                    return resultCandidateDto;
                });
    }

    @Override
    public List<RowHolder> tempGetArchiveRowsByMatchId(Long matchId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", matchId);
        return this.namedParameterJdbcTemplate.query(
                GET_ROWS_BY_ID,
                namedParameters,
                (rs, rowNum) -> {
                    RowHolder rowHolder = new RowHolder();

                    rowHolder.setId(rs.getLong(1));
                    rowHolder.setSportName(rs.getString(2));
                    rowHolder.setHomeTeam(rs.getString(3));
                    rowHolder.setAwayTeam(rs.getString(4));
                    rowHolder.setScoreTime(rs.getInt(5));
                    rowHolder.setScoreDetails(rs.getString(6));
                    rowHolder.setLocation(rs.getString(7));
                    rowHolder.setChampionship(rs.getString(8));
                    rowHolder.setNextGoal1Coef(rs.getDouble(9));
                    rowHolder.setNextGoalXCoef(rs.getDouble(10));
                    rowHolder.setNextGoal2Coef(rs.getDouble(11));
                    rowHolder.setRowTimeStamp(rs.getTimestamp(12));
                    rowHolder.setKickoffDate(rs.getTimestamp(13));

                    return rowHolder;
                });
    }

    @Override
    public Timestamp getLastAddedMatchTimestamp() {
        return namedParameterJdbcTemplate.queryForObject(
                GET_LAST_MATCH_DATE,
                new MapSqlParameterSource(),
                Timestamp.class
        );
    }

    @Override
    public List<Long> getUncompletedMatchIds(Timestamp from) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("kickoff_date", from);
        namedParameters.addValue("min_rows", 80);
        namedParameters.addValue("max_rows", 89);

        return this.namedParameterJdbcTemplate.query(
                GET_ARCHIVE_IDS,
                namedParameters,
                (rs, rowNum) -> rs.getLong(1));
    }

    @Override
    public void saveToArchive(RowHolder rowHolder) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", rowHolder.getId());
        namedParameters.addValue("sport_name", rowHolder.getSportName());
        namedParameters.addValue("home_team", rowHolder.getHomeTeam());
        namedParameters.addValue("away_team", rowHolder.getAwayTeam());
        namedParameters.addValue("score_time", rowHolder.getScoreTime());
        namedParameters.addValue("score_details", rowHolder.getScoreDetails());
        namedParameters.addValue("location", rowHolder.getLocation());
        namedParameters.addValue("championship", rowHolder.getChampionship());
        namedParameters.addValue("next_goal_1_coef", rowHolder.getNextGoal1Coef());
        namedParameters.addValue("next_goal_x_coef", rowHolder.getNextGoalXCoef());
        namedParameters.addValue("next_goal_2_coef", rowHolder.getNextGoal2Coef());
        namedParameters.addValue("timestamp_current", rowHolder.getRowTimeStamp());
        namedParameters.addValue("kickoff_date", rowHolder.getKickoffDate());
        namedParameters.addValue("reconstructed", rowHolder.isReconstructed());

        try {
            namedParameterJdbcTemplate.update(
                    SAVE_ARCHIVE_ENTRY,
                    namedParameters
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Long> getMatchCandidateIds(Timestamp from) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("kickoff_date", from);
        namedParameters.addValue("min_rows", 90);
        namedParameters.addValue("max_rows", 90);

        return this.namedParameterJdbcTemplate.query(
                GET_ARCHIVE_IDS,
                namedParameters,
                (rs, rowNum) -> rs.getLong(1));
    }

    @Override
    public List<RowHolder> getMatchRows(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        return this.namedParameterJdbcTemplate.query(
                GET_ROWS_BY_ID,
                namedParameters,
                (rs, rowNum) -> {
                    RowHolder rowHolder = new RowHolder();

                    rowHolder.setId(rs.getLong(1));
                    rowHolder.setSportName(rs.getString(2));
                    rowHolder.setHomeTeam(rs.getString(3));
                    rowHolder.setAwayTeam(rs.getString(4));
                    rowHolder.setScoreTime(rs.getInt(5));
                    rowHolder.setScoreDetails(rs.getString(6));

                    Map<String, Integer> scoreMap = getScoreMap(rowHolder.getScoreDetails());

                    rowHolder.setScore1(scoreMap.get("score1"));
                    rowHolder.setScore2(scoreMap.get("score2"));
                    rowHolder.setLocation(rs.getString(7));
                    rowHolder.setChampionship(rs.getString(8));
                    rowHolder.setNextGoal1Coef(rs.getDouble(9));
                    rowHolder.setNextGoalXCoef(rs.getDouble(10));
                    rowHolder.setNextGoal2Coef(rs.getDouble(11));
                    rowHolder.setRowTimeStamp(rs.getTimestamp(12));
                    rowHolder.setKickoffDate(rs.getTimestamp(13));
                    rowHolder.setReconstructed(rs.getBoolean(14));

                    return rowHolder;
                });
    }

    @Override
    public void saveMatchData(Match match) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("id", match.getId());
        namedParameters.addValue("sport_name", match.getSportName());
        namedParameters.addValue("location", match.getLocation());
        namedParameters.addValue("championship", match.getChampionship());
        namedParameters.addValue("home_team", match.getHomeTeam());
        namedParameters.addValue("away_team", match.getAwayTeam());
        namedParameters.addValue("planned_kickoff_date", match.getPlannedKickoffDate());
        namedParameters.addValue("actual_kickoff_date", match.getActualKickoffDate());
        namedParameters.addValue("initially_completed", match.isInitiallyCompleted());
        namedParameters.addValue("score_confirmed", false);

        namedParameterJdbcTemplate.update(
                SAVE_MATCH,
                namedParameters
        );
    }

    @Override
    public void saveDetail(StatisticsRowHolder detail) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("id", detail.getId());
        namedParameters.addValue("score_time", detail.getScoreTime());
        namedParameters.addValue("score1", detail.getScore1());
        namedParameters.addValue("score2", detail.getScore2());
        namedParameters.addValue("next_goal_1_coef", detail.getNextGoal1Coef());
        namedParameters.addValue("next_goal_x_coef", detail.getNextGoalXCoef());
        namedParameters.addValue("next_goal_2_coef", detail.getNextGoal2Coef());
        namedParameters.addValue("team_1_scored", (detail.isTeam1Scored() ? true : null));
        namedParameters.addValue("team_2_scored", (detail.isTeam2Scored() ? true : null));
        namedParameters.addValue("reconstructed", detail.isReconstructed());

        namedParameterJdbcTemplate.update(
                SAVE_DETAIL,
                namedParameters
        );
    }

    @Override
    public void savePeriod(Period period) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("name", period.getName());
        namedParameters.addValue("from", period.getFrom());
        namedParameters.addValue("to", period.getTo());

        namedParameterJdbcTemplate.update(
                SAVE_PERIOD,
                namedParameters
        );
    }

    private Map<String, Integer> getScoreMap(String scoreDetails) {
        int score1 = 0;
        int score2 = 0;

        Map<String, Integer> resultMap = new HashMap<>();

        String[] scorePartsByPeriods = scoreDetails.split(";");
        for (String scorePartByPeriod : scorePartsByPeriods) {
            scorePartByPeriod = scorePartByPeriod.trim();
            String[] scores = scorePartByPeriod.split(":");

            int localScore1 = Integer.parseInt(scores[0]);
            int localScore2 = Integer.parseInt(scores[1]);

            score1 += localScore1;
            score2 += localScore2;
        }

        resultMap.put("score1", score1);
        resultMap.put("score2", score2);

        return resultMap;
    }
}
