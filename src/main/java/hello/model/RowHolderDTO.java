package hello.model;

import java.sql.Timestamp;

public class RowHolderDTO {
    private long id;
    private String name;
    private String sportName;
    private int scoreTime;
    private String scoreDetails;
    private String location;
    private String championship;
    private double nextGoal1Coef;
    private double nextGoalXCoef;
    private double nextGoal2Coef;
    private Timestamp rowTimeStamp;
    private boolean reconstructed;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
