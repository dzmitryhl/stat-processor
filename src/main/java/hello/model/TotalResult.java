package hello.model;

import java.util.ArrayList;
import java.util.List;

public class TotalResult {
    double total;
    int numberOfBets;
    List<IntervalResult> partialResults;

    public TotalResult() {
        this.partialResults = new ArrayList<>();
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

    public List<IntervalResult> getPartialResults() {
        return partialResults;
    }

    public void setPartialResults(List<IntervalResult> partialResults) {
        this.partialResults = partialResults;
    }
}
