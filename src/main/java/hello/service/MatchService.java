package hello.service;


import hello.dto.ResultCandidateDto;
import hello.model.Match;
import hello.model.Period;
import hello.model.RowHolder;
import hello.model.StatisticsRowHolder;

import java.sql.Timestamp;
import java.util.List;

public interface MatchService {
    List<Long> getMatchIds(Timestamp startDate, Timestamp endDate, boolean notConfirmed);
    List<Long> getMatchIds(Timestamp startDate, Timestamp endDate);
    List<Long> getRandomMatchIds(long number);
    List<Long> getAllMatchIds();
    Match getMatchById(Long id);
    StatisticsRowHolder getDetail(Long matchId, int scoreTime);
    List<StatisticsRowHolder> getDetails(Long matchId);
    List<ResultCandidateDto> getResultCandidates(Long matchId);

    List<RowHolder> tempGetArchiveRowsByMatchId(Long matchId);
    Timestamp getLastAddedMatchTimestamp();

    List<Long> getUncompletedMatchIds(Timestamp from);
    void saveToArchive(RowHolder rowHolder);
    List<Long> getMatchCandidateIds(Timestamp from);
    List<RowHolder> getMatchRows(Long id);

    void saveMatch(Match match);
    void savePeriod(Period period);

    void updateScoreConfirmed(long matchId, boolean confirmed);
    void updateMatchResult(long matchId, int homeTeamScore, int awayTeamScore);
}
