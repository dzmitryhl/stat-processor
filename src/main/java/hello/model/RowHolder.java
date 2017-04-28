package hello.model;

import java.sql.Timestamp;

public class RowHolder {
    private long id;
    private String sportName;
    private String homeTeam;
    private String awayTeam;
    private int scoreTime;
    private String scoreDetails;
    private String location;
    private String championship;
    private double nextGoal1Coef;
    private double nextGoalXCoef;
    private double nextGoal2Coef;
    private int score1;
    private int score2;
    private boolean team1Scored;
    private boolean team2Scored;
    private Timestamp rowTimeStamp;
    private boolean reconstructed;
    private Timestamp kickoffDate;

    public RowHolder() {

    }

    public RowHolder(RowHolder rowHolder) {
        if (rowHolder != null) {
            this.id = rowHolder.getId();
            this.sportName = rowHolder.getSportName();
            this.homeTeam = rowHolder.getHomeTeam();
            this.awayTeam = rowHolder.getAwayTeam();
            this.scoreTime = rowHolder.getScoreTime();
            this.scoreDetails = rowHolder.getScoreDetails();
            this.location = rowHolder.getLocation();
            this.championship = rowHolder.getChampionship();
            this.nextGoal1Coef = rowHolder.getNextGoal1Coef();
            this.nextGoalXCoef = rowHolder.getNextGoalXCoef();
            this.nextGoal2Coef = rowHolder.getNextGoal2Coef();
            this.kickoffDate = rowHolder.getKickoffDate();
            this.rowTimeStamp = rowHolder.getRowTimeStamp();
        }

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public int getScoreTime() {
        return scoreTime;
    }

    public void setScoreTime(int scoreTime) {
        this.scoreTime = scoreTime;
    }

    public String getScoreDetails() {
        return scoreDetails;
    }

    public void setScoreDetails(String scoreDetails) {
        this.scoreDetails = scoreDetails;
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

    public double getNextGoal1Coef() {
        return nextGoal1Coef;
    }

    public void setNextGoal1Coef(double nextGoal1Coef) {
        this.nextGoal1Coef = nextGoal1Coef;
    }

    public double getNextGoalXCoef() {
        return nextGoalXCoef;
    }

    public void setNextGoalXCoef(double nextGoalXCoef) {
        this.nextGoalXCoef = nextGoalXCoef;
    }

    public double getNextGoal2Coef() {
        return nextGoal2Coef;
    }

    public void setNextGoal2Coef(double nextGoal2Coef) {
        this.nextGoal2Coef = nextGoal2Coef;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public boolean hasTeam1Scored() {
        return team1Scored;
    }

    public void setTeam1Scored(boolean team1Scored) {
        this.team1Scored = team1Scored;
    }

    public boolean hasTeam2Scored() {
        return team2Scored;
    }

    public void setTeam2Scored(boolean team2Scored) {
        this.team2Scored = team2Scored;
    }

    public boolean isTeam1Scored() {
        return team1Scored;
    }

    public boolean isTeam2Scored() {
        return team2Scored;
    }

    public Timestamp getRowTimeStamp() {
        return rowTimeStamp;
    }

    public void setRowTimeStamp(Timestamp rowTimeStamp) {
        this.rowTimeStamp = rowTimeStamp;
    }

    public boolean isReconstructed() {
        return reconstructed;
    }

    public void setReconstructed(boolean reconstructed) {
        this.reconstructed = reconstructed;
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

    public Timestamp getKickoffDate() {
        return kickoffDate;
    }

    public void setKickoffDate(Timestamp kickoffDate) {
        this.kickoffDate = kickoffDate;
    }
}
