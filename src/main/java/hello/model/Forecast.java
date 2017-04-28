package hello.model;

public class Forecast {
    private long eventId;
    private int minute;
    private Result result;
    private double coefficient;
    private double betSum;
    private boolean isCompleted;
    private boolean isWinning;
    private String location;
    private String competition;
    private int score1;
    private int score2;

    public Forecast(long eventId, int minute, Result result, double coefficient, double betSum) {
        this.eventId = eventId;
        this.minute = minute;
        this.result = result;
        this.coefficient = coefficient;
        this.betSum = betSum;
    }

    public Forecast(String location, String competition, int score1, int score2, long eventId, int minute, Result result, double coefficient, double betSum, boolean isCompleted, boolean isWinning) {
        this.location = location;
        this.competition = competition;
        this.score1 = score1;
        this.score2 = score2;
        this.eventId = eventId;
        this.minute = minute;
        this.result = result;
        this.coefficient = coefficient;
        this.betSum = betSum;
        this.isCompleted = isCompleted;
        this.isWinning = isWinning;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public double getBetSum() {
        return betSum;
    }

    public void setBetSum(double betSum) {
        this.betSum = betSum;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isWinning() {
        return isWinning;
    }

    public void setWinning(boolean winning) {
        isWinning = winning;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
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
}
