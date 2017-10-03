package hello.dto.request;

import hello.model.Interval;
import hello.model.StrategyType;

public class ForecastRequestDTO {
    private Long periodId;
    private StrategyType strategyType;
    private Long favoriteScoreMin;
    private Long favoriteScoreMax;
    private Double favoriteCoefficientMin;
    private Double favoriteCoefficientMax;
    private Long outsiderScoreMin;
    private Long outsiderScoreMax;
    private Double outsiderCoefficientMin;
    private Double outsiderCoefficientMax;
    private Integer timeMin;
    private Integer timeMax;
    private boolean showHistory;
    private boolean showDetails;

    private boolean loadAll;
    private Integer loadNRandom;
    private Interval interval;

    public boolean isLoadAll() {
        return loadAll;
    }

    public void setLoadAll(boolean loadAll) {
        this.loadAll = loadAll;
    }

    public Integer getLoadNRandom() {
        return loadNRandom;
    }

    public void setLoadNRandom(Integer loadNRandom) {
        this.loadNRandom = loadNRandom;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    //    private List<Interval> intervals;

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public void setResultExpectation(StrategyType strategyType) {
        this.strategyType = strategyType;
    }

    public StrategyType getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(StrategyType strategyType) {
        this.strategyType = strategyType;
    }

    public Long getFavoriteScoreMin() {
        return favoriteScoreMin;
    }

    public void setFavoriteScoreMin(Long favoriteScoreMin) {
        this.favoriteScoreMin = favoriteScoreMin;
    }

    public Double getFavoriteCoefficientMin() {
        return favoriteCoefficientMin;
    }

    public void setFavoriteCoefficientMin(Double favoriteCoefficientMin) {
        this.favoriteCoefficientMin = favoriteCoefficientMin;
    }

    public Long getOutsiderScoreMin() {
        return outsiderScoreMin;
    }

    public void setOutsiderScoreMin(Long outsiderScoreMin) {
        this.outsiderScoreMin = outsiderScoreMin;
    }

    public Double getOutsiderCoefficientMin() {
        return outsiderCoefficientMin;
    }

    public void setOutsiderCoefficientMin(Double outsiderCoefficientMin) {
        this.outsiderCoefficientMin = outsiderCoefficientMin;
    }

    public Integer getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(Integer timeMin) {
        this.timeMin = timeMin;
    }

    public Integer getTimeMax() {
        return timeMax;
    }

    public void setTimeMax(Integer timeMax) {
        this.timeMax = timeMax;
    }

    public Long getFavoriteScoreMax() {
        return favoriteScoreMax;
    }

    public void setFavoriteScoreMax(Long favoriteScoreMax) {
        this.favoriteScoreMax = favoriteScoreMax;
    }

    public Double getFavoriteCoefficientMax() {
        return favoriteCoefficientMax;
    }

    public void setFavoriteCoefficientMax(Double favoriteCoefficientMax) {
        this.favoriteCoefficientMax = favoriteCoefficientMax;
    }

    public Long getOutsiderScoreMax() {
        return outsiderScoreMax;
    }

    public void setOutsiderScoreMax(Long outsiderScoreMax) {
        this.outsiderScoreMax = outsiderScoreMax;
    }

    public Double getOutsiderCoefficientMax() {
        return outsiderCoefficientMax;
    }

    public void setOutsiderCoefficientMax(Double outsiderCoefficientMax) {
        this.outsiderCoefficientMax = outsiderCoefficientMax;
    }

//    public List<Interval> getIntervals() {
//        return intervals;
//    }
//
//    public void setIntervals(List<Interval> intervals) {
//        this.intervals = intervals;
//    }

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
