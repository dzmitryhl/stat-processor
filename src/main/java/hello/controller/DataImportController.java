package hello.controller;

import hello.dto.ResultCandidateDto;
import hello.dto.ResultTempDto;
import hello.model.*;
import hello.service.MatchService;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

@RestController
public class DataImportController {

    @Autowired
    MatchService matchService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/results-check", method = RequestMethod.POST)
    public ResponseEntity checkResults(@RequestBody MatchResultRequestDTO matchResultRequestDTO) {
        List<Match> notConfirmedScore = new ArrayList<>();

        Timestamp startDate = new Timestamp(matchResultRequestDTO.getStartDate().getTime());
        Timestamp endDate = new Timestamp(matchResultRequestDTO.getEndDate().getTime());

        List<Long> ids = matchService.getMatchIds(startDate, endDate, matchResultRequestDTO.isUncompletedOnly());

        for (long id : ids) {

            Match match = matchService.getMatchById(id);
            List<StatisticsRowHolder> details = new ArrayList<>();
            details.add(matchService.getDetail(id, 90));
            match.setDetails(details);

            List<ResultCandidateDto> resultCandidateDtoList = matchService.getResultCandidates(id);

            checkAndModifyMatchResult(match, resultCandidateDtoList);

            if (!match.isScoreConfirmed()) {
                notConfirmedScore.add(match);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(notConfirmedScore);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/startDataImport", method = RequestMethod.POST)
    public ResponseEntity startDataImport(@RequestBody Object object) {
        Timestamp lastAddedMatchTimestamp = matchService.getLastAddedMatchTimestamp();
        List<Long> ids = matchService.getUncompletedMatchIds(lastAddedMatchTimestamp);

        List<List<RowHolder>> uncompletedMatches = new ArrayList<>();

        for (long id : ids) {
            List<RowHolder> rowHolderList = matchService.tempGetArchiveRowsByMatchId(id);
            rowHolderList.sort(Comparator.comparingInt(RowHolder::getScoreTime));

            uncompletedMatches.add(rowHolderList);
        }

        List<RowHolder> modifiedRows = new ArrayList<>();

        for (List<RowHolder> rowHolderList : uncompletedMatches) {
            List<RowHolder> modifiedRowHolderList = new ArrayList<>(90);
            for (int i = 0, j = 0; i < 90; i++) {
                RowHolder rowHolder = null;
                if (j <= rowHolderList.size() - 1) {
                    rowHolder = rowHolderList.get(j);
                }
                if (rowHolder != null && rowHolder.getScoreTime() == i + 1) {
                    modifiedRowHolderList.add(i, rowHolder);
                    j++;
                } else {
                    modifiedRowHolderList.add(i, null);
                }
            }
            for (int i = 0; i < modifiedRowHolderList.size(); i++) {
                if (modifiedRowHolderList.get(i) == null) {
                    RowHolder rowHolder = new RowHolder(getRowHolderCandidate(modifiedRowHolderList, i));
                    rowHolder.setReconstructed(true);
                    rowHolder.setScoreTime(i + 1);
                    modifiedRows.add(rowHolder);
                }
            }
        }

        for (RowHolder row : modifiedRows) {
            matchService.saveToArchive(row);
        }

        List<Long> matchIds = matchService.getMatchCandidateIds(lastAddedMatchTimestamp);

        for (Long id : matchIds) {
            List<RowHolder> matchRows = matchService.getMatchRows(id);
            Match match = convertToMatch(matchRows);
            matchService.saveMatch(match);
        }

        Timestamp newlyAddedMatchTimestamp = matchService.getLastAddedMatchTimestamp();
        matchService.savePeriod(new Period("new_part", lastAddedMatchTimestamp, newlyAddedMatchTimestamp));

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    private void checkAndModifyMatchResult(Match match, List<ResultCandidateDto> resultCandidates) {
        String homeTeam = match.getHomeTeam();
        String awayTeam = match.getAwayTeam();

        String homeTeamSearchTeam = getSearchTerm(homeTeam);
        String awayTeamSearchTeam = getSearchTerm(awayTeam);

        StatisticsRowHolder lastRow = match.getDetails().get(0);

        if (match.getDetails().size() != 1) {
            //throw new Exception("Filler for now");
        }

        int score1 = lastRow.getScore1();
        int score2 = lastRow.getScore2();

        for (ResultCandidateDto resultCandidate : resultCandidates) {
            String resultHomeTeamTerm = getSearchTerm(resultCandidate.getHomeTeam());
            String resultAwayTeamTerm = getSearchTerm(resultCandidate.getAwayTeam());

            if (homeTeamSearchTeam.equals(resultHomeTeamTerm) || awayTeamSearchTeam.equals(resultAwayTeamTerm)) {
                Map<String, Integer> resultScoreMap = getResultScoreMap(resultCandidate.getResult());
                Integer resultScore1 = resultScoreMap.get("score1");
                Integer resultScore2 = resultScoreMap.get("score2");

                if (resultScore1 == null || resultScore2 == null) {
                    match.setScoreConfirmed(false);
                    resultCandidates = new ArrayList<>();
                    resultCandidates.add(resultCandidate);
                    break;
                }

                if (score1 != resultScore1 || score2 != resultScore2) {
                    lastRow.setScore1(resultScore1);
                    lastRow.setScore2(resultScore2);
                    lastRow.setReconstructed(true);
                }
                match.setResultCandidates(null);
                match.setScoreConfirmed(true);
            }
        }

        if (!match.isScoreConfirmed()) {
            match.setResultCandidates(resultCandidates);
        }

    }

    private Match convertToMatch(List<RowHolder> rowHolderList) {
        rowHolderList.sort(Comparator.comparingInt(RowHolder::getScoreTime));
        Match match = new Match();
        RowHolder firstRow = rowHolderList.get(0);

        match.setId(firstRow.getId());
        match.setHomeTeam(firstRow.getHomeTeam());
        match.setAwayTeam(firstRow.getAwayTeam());
        match.setSportName(firstRow.getSportName());
        match.setLocation(firstRow.getLocation());
        match.setChampionship(firstRow.getChampionship());
        match.setPlannedKickoffDate(firstRow.getKickoffDate());
        match.setActualKickoffDate(firstRow.getRowTimeStamp());

        List<StatisticsRowHolder> statisticsRowHolders = new ArrayList<>();

        boolean initiallyCompleted = true;

        for (int i = 0; i < rowHolderList.size(); i++) {

            RowHolder rowHolder = rowHolderList.get(i);

            StatisticsRowHolder statisticsRowHolder = new StatisticsRowHolder();

            statisticsRowHolder.setId(rowHolder.getId());
            statisticsRowHolder.setScore1(rowHolder.getScore1());
            statisticsRowHolder.setScore2(rowHolder.getScore2());
            statisticsRowHolder.setScoreTime(rowHolder.getScoreTime());
            statisticsRowHolder.setNextGoal1Coef(rowHolder.getNextGoal1Coef());
            statisticsRowHolder.setNextGoalXCoef(rowHolder.getNextGoalXCoef());
            statisticsRowHolder.setNextGoal2Coef(rowHolder.getNextGoal2Coef());

            if (i > 0) {
                RowHolder prevRowHolder = rowHolderList.get(i - 1);
                statisticsRowHolder.setTeam1Scored(prevRowHolder.getScore1() != rowHolder.getScore1());
                statisticsRowHolder.setTeam2Scored(prevRowHolder.getScore2() != rowHolder.getScore2());
            }

            if (rowHolder.isReconstructed()) {
                initiallyCompleted = false;
            }

            statisticsRowHolder.setReconstructed(rowHolder.isReconstructed());

            statisticsRowHolders.add(statisticsRowHolder);
        }

        match.setInitiallyCompleted(initiallyCompleted);
        match.setDetails(statisticsRowHolders);

        return match;
    }


    private RowHolder getRowHolderCandidate(List<RowHolder> rowHolderList, int index) {
        for (int i = index; i >= 0; i--) {
            if (rowHolderList.get(i) != null) {
                return rowHolderList.get(i);
            }
        }
        for (int i = index; i < 90; i++) {
            if (rowHolderList.get(i) != null) {
                return rowHolderList.get(i);
            }
        }
        return null;
    }

    private Map<String, Integer> getResultScoreMap(String scoreDetails) {
        Integer score1 = null;
        Integer score2 = null;

        Map<String, Integer> resultMap = new HashMap<>();

        if (scoreDetails.matches("[0-9-]+")) {
            scoreDetails = scoreDetails.trim();
            String[] scores = scoreDetails.split("-");

            score1 = Integer.parseInt(scores[0]);
            score2 = Integer.parseInt(scores[1]);
        }

        resultMap.put("score1", score1);
        resultMap.put("score2", score2);

        return resultMap;
    }

    private String getSearchTerm(String initial) {
        if (initial == null) {
            return null;
        }
        String modified =  initial.replaceAll("\\d\\.+", "");
        String[] parts = modified.split("\\s+");
        List<String> filteredParts = new ArrayList<>();
        for (String part : parts) {
            if (!isInUpperCase(part)) {
                filteredParts.add(part.trim());
            }
        }
        return String.join(" ", filteredParts).toLowerCase();
    }

    private boolean isInUpperCase(String str) {
        return !(str == null || str.isEmpty()) && str.toUpperCase().equals(str);
    }
}
