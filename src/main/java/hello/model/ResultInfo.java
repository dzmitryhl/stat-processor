package hello.model;

import java.util.List;

public class ResultInfo {
    double total;
    int numberOfBets;
    List<Forecast> forecasts;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getNumberOfBets() {
        return numberOfBets;
    }

    public void setNumberOfBets(int numberOfBets) {
        this.numberOfBets = numberOfBets;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }
}
