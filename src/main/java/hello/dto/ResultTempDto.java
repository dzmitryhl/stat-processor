package hello.dto;

import java.sql.Timestamp;

public class ResultTempDto {
    private long id;
    private Timestamp kickoffDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getKickoffDate() {
        return kickoffDate;
    }

    public void setKickoffDate(Timestamp kickoffDate) {
        this.kickoffDate = kickoffDate;
    }
}
