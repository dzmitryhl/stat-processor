package hello.model;

import java.util.ArrayList;
import java.util.List;

public class TotalResult {
    double total;
    int numberOfBets;
    List<IntervalResult> intervalResults;

    public TotalResult() {
        this.intervalResults = new ArrayList<>();
    }

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

    public List<IntervalResult> getIntervalResults() {
        return intervalResults;
    }

    public void setIntervalResults(List<IntervalResult> intervalResults) {
        this.intervalResults = intervalResults;
    }
}
