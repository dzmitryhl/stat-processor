package hello.model;

public class Row {
    private Long id;
    private int scoreTime;
    private int score1;
    private int score2;
    private double nextGoal1Coef;
    private double nextGoalXCoef;
    private double nextGoal2Coef;
    private boolean team1Scored;
    private boolean team2Scored;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScoreTime() {
        return scoreTime;
    }

    public void setScoreTime(int scoreTime) {
        this.scoreTime = scoreTime;
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

    public boolean isTeam1Scored() {
        return team1Scored;
    }

    public void setTeam1Scored(boolean team1Scored) {
        this.team1Scored = team1Scored;
    }

    public boolean isTeam2Scored() {
        return team2Scored;
    }

    public void setTeam2Scored(boolean team2Scored) {
        this.team2Scored = team2Scored;
    }
}
