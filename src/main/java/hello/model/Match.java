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

    public double checkForecast(Forecast forecast) {

        for (int i = forecast.getMinute(); i < this.details.size(); i++) {

            StatisticsRowHolder row = this.getDetails().get(i);

            if (row.isTeam1Scored()) {
                if (forecast.getResult() == Result.TEAM_1_SCORED) {
                    return forecast.getCoefficient() * forecast.getBetSum();
                } else {
                    return -forecast.getBetSum();
                }
            } else if (row.isTeam2Scored()) {
                if (forecast.getResult() == Result.TEAM_2_SCORED) {
                    return forecast.getCoefficient() * forecast.getBetSum();
                } else {
                    return -forecast.getBetSum();
                }
            }
        }

        return -forecast.getBetSum();

    }


    public double checkForecast(Forecast forecast, int currentMinute) {

        StatisticsRowHolder row = this.getDetails().get(currentMinute - 1);

        if (row.isTeam1Scored()) {
            forecast.setCompleted(true);
            if (forecast.getResult() == Result.TEAM_1_SCORED) {
                forecast.setWinning(true);
                return forecast.getCoefficient() * forecast.getBetSum();
            } else {
                forecast.setWinning(false);
                return 0;
            }
        } else if (row.isTeam2Scored()) {
            forecast.setCompleted(true);
            if (forecast.getResult() == Result.TEAM_2_SCORED) {
                forecast.setWinning(true);
                return forecast.getCoefficient() * forecast.getBetSum();
            } else {
                forecast.setWinning(false);
                return 0;
            }
        } else if (row.getScoreTime() == 90 && forecast.getResult() == Result.TEAM_X_SCORED) {
            forecast.setCompleted(true);
            forecast.setWinning(true);
            return forecast.getCoefficient() * forecast.getBetSum();
        }

        return 0;

    }
}
