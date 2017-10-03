package hello.service;

import hello.dao.MatchDAO;
import hello.dto.ResultCandidateDto;
import hello.model.Match;
import hello.model.Period;
import hello.model.RowHolder;
import hello.model.StatisticsRowHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchDAO matchDAO;


    @Override
    public List<Long> getMatchIds(Timestamp startDate, Timestamp endDate, boolean notConfirmed) {
        return matchDAO.getMatchIds(startDate, endDate, notConfirmed);
    }

    @Override
    public List<Long> getMatchIds(Timestamp startDate, Timestamp endDate) {
        return matchDAO.getMatchIds(startDate, endDate, false);
    }

    @Override
    public List<Long> getRandomMatchIds(long number) {
        return matchDAO.getRandomMatchIds(number);
    }

    @Override
    public List<Long> getAllMatchIds() {
        return matchDAO.getAllMatchIds();
    }

    @Override
    public Match getMatchById(Long id) {
        return matchDAO.getMatchById(id);
    }

    @Override
    public StatisticsRowHolder getDetail(Long matchId, int scoreTime) {
        return matchDAO.getDetail(matchId, scoreTime);
    }

    @Override
    public List<StatisticsRowHolder> getDetails(Long matchId) {
        return matchDAO.getDetails(matchId);
    }

    @Override
    public List<ResultCandidateDto> getResultCandidates(Long matchId) {
        return matchDAO.getResultCandidates(matchId);
    }

    @Override
    public List<RowHolder> tempGetArchiveRowsByMatchId(Long matchId) {
        return matchDAO.tempGetArchiveRowsByMatchId(matchId);
    }

    @Override
    public Timestamp getLastAddedMatchTimestamp() {
        return matchDAO.getLastAddedMatchTimestamp();
    }

    @Override
    public List<Long> getUncompletedMatchIds(Timestamp from) {
        return matchDAO.getUncompletedMatchIds(from);
    }

    @Override
    public void saveToArchive(RowHolder rowHolder) {
        matchDAO.saveToArchive(rowHolder);
    }

    @Override
    public List<Long> getMatchCandidateIds(Timestamp from) {
        return matchDAO.getMatchCandidateIds(from);
    }

    @Override
    public List<RowHolder> getMatchRows(Long id) {
        return matchDAO.getMatchRows(id);
    }

    @Override
    public void saveMatch(Match match) {
        matchDAO.saveMatchData(match);
        for (StatisticsRowHolder detail : match.getDetails()) {
            matchDAO.saveDetail(detail);
        }
    }

    @Override
    public void savePeriod(Period period) {

    }

    @Override
    public void updateScoreConfirmed(long matchId, boolean confirmed) {
        matchDAO.updateScoreConfirmed(matchId, confirmed);
    }

    @Override
    public void updateMatchResult(long matchId, int homeTeamScore, int awayTeamScore) {
        matchDAO.updateMatchResult(matchId, homeTeamScore, awayTeamScore);
        matchDAO.updateScoreConfirmed(matchId, true);
    }


}
