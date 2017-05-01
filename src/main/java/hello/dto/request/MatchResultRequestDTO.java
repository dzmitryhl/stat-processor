package hello.dto.request;

import java.util.Date;

public class MatchResultRequestDTO {
    private Date startDate;
    private Date endDate;
    private boolean uncompletedOnly;

    public boolean isUncompletedOnly() {
        return uncompletedOnly;
    }

    public void setUncompletedOnly(boolean uncompletedOnly) {
        this.uncompletedOnly = uncompletedOnly;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
