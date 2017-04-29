package hello.model;

public class Forecast {
    private int minute;
    private Result result;
    private double coefficient;
    private double betSum;
    private boolean isCompleted;
    private boolean isWinning;
    private Match match;

    public Forecast(Match match, int minute, Result result, double coefficient, double betSum) {
        this.match = match;
        this.minute = minute;
        this.result = result;
        this.coefficient = coefficient;
        this.betSum = betSum;
    }

    public Forecast(Match match, int minute, Result result, double coefficient, double betSum, boolean isCompleted, boolean isWinning) {
        this.match = match;
        this.minute = minute;
        this.result = result;
        this.coefficient = coefficient;
        this.betSum = betSum;
        this.isCompleted = isCompleted;
        this.isWinning = isWinning;
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

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public double check(int currentMinute) {

        StatisticsRowHolder row = this.match.getDetails().get(currentMinute - 1);
        Forecast forecast = this;

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
