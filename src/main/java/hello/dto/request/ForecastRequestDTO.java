package hello.dto.request;

import hello.model.Interval;
import hello.model.RequestResultExpectation;

import java.sql.Timestamp;
import java.util.List;

public class ForecastRequestDTO {
    private long periodId;
    private RequestResultExpectation requestResultExpectation;
    private long playTeamMinScore;
    private long playTeamMaxScore;
    private double playTeamMinCoefficient;
    private double playTeamMaxCoefficient;
    private long otherTeamMinScore;
    private long otherTeamMaxScore;
    private double otherTeamMinCoefficient;
    private double otherTeamMaxCoefficient;
    private int scoreTimeMin;
    private int scoreTimeMax;
    private boolean showHistory;
    private boolean showDetails;
    private List<Interval> intervals;

    public long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }

    public void setResultExpectation(RequestResultExpectation requestResultExpectation) {
        this.requestResultExpectation = requestResultExpectation;
    }

    public RequestResultExpectation getRequestResultExpectation() {
        return requestResultExpectation;
    }

    public void setRequestResultExpectation(RequestResultExpectation requestResultExpectation) {
        this.requestResultExpectation = requestResultExpectation;
    }

    public long getPlayTeamMinScore() {
        return playTeamMinScore;
    }

    public void setPlayTeamMinScore(long playTeamMinScore) {
        this.playTeamMinScore = playTeamMinScore;
    }

    public double getPlayTeamMinCoefficient() {
        return playTeamMinCoefficient;
    }

    public void setPlayTeamMinCoefficient(double playTeamMinCoefficient) {
        this.playTeamMinCoefficient = playTeamMinCoefficient;
    }

    public long getOtherTeamMinScore() {
        return otherTeamMinScore;
    }

    public void setOtherTeamMinScore(long otherTeamMinScore) {
        this.otherTeamMinScore = otherTeamMinScore;
    }

    public double getOtherTeamMinCoefficient() {
        return otherTeamMinCoefficient;
    }

    public void setOtherTeamMinCoefficient(double otherTeamMinCoefficient) {
        this.otherTeamMinCoefficient = otherTeamMinCoefficient;
    }

    public int getScoreTimeMin() {
        return scoreTimeMin;
    }

    public void setScoreTimeMin(int scoreTimeMin) {
        this.scoreTimeMin = scoreTimeMin;
    }

    public int getScoreTimeMax() {
        return scoreTimeMax;
    }

    public void setScoreTimeMax(int scoreTimeMax) {
        this.scoreTimeMax = scoreTimeMax;
    }

    public long getPlayTeamMaxScore() {
        return playTeamMaxScore;
    }

    public void setPlayTeamMaxScore(long playTeamMaxScore) {
        this.playTeamMaxScore = playTeamMaxScore;
    }

    public double getPlayTeamMaxCoefficient() {
        return playTeamMaxCoefficient;
    }

    public void setPlayTeamMaxCoefficient(double playTeamMaxCoefficient) {
        this.playTeamMaxCoefficient = playTeamMaxCoefficient;
    }

    public long getOtherTeamMaxScore() {
        return otherTeamMaxScore;
    }

    public void setOtherTeamMaxScore(long otherTeamMaxScore) {
        this.otherTeamMaxScore = otherTeamMaxScore;
    }

    public double getOtherTeamMaxCoefficient() {
        return otherTeamMaxCoefficient;
    }

    public void setOtherTeamMaxCoefficient(double otherTeamMaxCoefficient) {
        this.otherTeamMaxCoefficient = otherTeamMaxCoefficient;
    }

    public List<Interval> getIntervals() {
        return intervals;
    }

    public void setIntervals(List<Interval> intervals) {
        this.intervals = intervals;
    }

    public boolean isShowHistory() {
        return showHistory;
    }

    public void setShowHistory(boolean showHistory) {
        this.showHistory = showHistory;
    }

    public boolean isShowDetails() {
        return showDetails;
    }

    public void setShowDetails(boolean showDetails) {
        this.showDetails = showDetails;
    }
}
