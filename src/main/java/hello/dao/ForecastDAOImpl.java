package hello.dao;

import hello.dto.request.ForecastRequestDTO;
import hello.model.IntervalResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ForecastDAOImpl implements ForecastDAO {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static String GET_MATCH_IDS_BY_PERIOD =
            "select id " +
            "from matches " +
            "where " +
                "planned_kickoff_date > " +
                    "(select \"from\" from periods where id = :period_id) and  " +
                "planned_kickoff_date <= " +
                    "(select \"to\" from periods where id = :period_id)";

    @Override
    public List<Long> getMatchIdsByPeriod(Long periodId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("period_id", periodId);

        return this.namedParameterJdbcTemplate.query(
                GET_MATCH_IDS_BY_PERIOD,
                namedParameters,
                (rs, rowNum) -> rs.getLong(1));
    }

    @Override
    public IntervalResult applyStrategy(ForecastRequestDTO forecastRequestDTO) {
        return null;
    }
}
