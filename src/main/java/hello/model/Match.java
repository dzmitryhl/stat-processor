package hello.model;

import hello.dto.ResultCandidateDto;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class Match {
    private Long id;
    private String sportName;
    private String homeTeam;
    private String awayTeam;
    private String location;
    private String championship;
    private boolean initiallyCompleted;
    private List<StatisticsRowHolder> details;
    private Timestamp plannedKickoffDate;
    private Timestamp actualKickoffDate;
    private List<ResultCandidateDto> resultCandidates;

    public Match() {}

    public Match(Match match) {
        this.id = match.getId();
        this.sportName = match.getSportName();
        this.homeTeam = match.getHomeTeam();
        this.awayTeam = match.getAwayTeam();
        this.location = match.getLocation();
        this.championship = match.getChampionship();
        this.initiallyCompleted = match.isInitiallyCompleted();
        this.details = match.getDetails();
        this.plannedKickoffDate = match.getPlannedKickoffDate();
        this.actualKickoffDate = match.getActualKickoffDate();
        this.resultCandidates = match.getResultCandidates();
    }

    public List<ResultCandidateDto> getResultCandidates() {
        return resultCandidates;
    }

    public void setResultCandidates(List<ResultCandidateDto> resultCandidates) {
        this.resultCandidates = resultCandidates;
    }

    public boolean isScoreConfirmed() {
        return scoreConfirmed;
    }

    public void setScoreConfirmed(boolean scoreConfirmed) {
        this.scoreConfirmed = scoreConfirmed;
    }

    private boolean scoreConfirmed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getChampionship() {
        return championship;
    }

    public void setChampionship(String championship) {
        this.championship = championship;
    }

    public boolean isInitiallyCompleted() {
        return initiallyCompleted;
    }

    public void setInitiallyCompleted(boolean initiallyCompleted) {
        this.initiallyCompleted = initiallyCompleted;
    }

    public List<StatisticsRowHolder> getDetails() {
        return details;
    }

    public void setDetails(List<StatisticsRowHolder> details) {
        this.details = details;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Timestamp getPlannedKickoffDate() {
        return plannedKickoffDate;
    }

    public void setPlannedKickoffDate(Timestamp plannedKickoffDate) {
        this.plannedKickoffDate = plannedKickoffDate;
    }

    public Timestamp getActualKickoffDate() {
        return actualKickoffDate;
    }

    public void setActualKickoffDate(Timestamp actualKickoffDate) {
        this.actualKickoffDate = actualKickoffDate;
    }
}
