package hello.dto;

import java.sql.Timestamp;

public class ResultCandidateDto {
    private String championship;
    private String homeTeam;
    private String awayTeam;
    private String result;

    public String getChampionship() {

        return championship;
    }

    public void setChampionship(String championship) {
        this.championship = championship;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
