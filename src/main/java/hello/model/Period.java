package hello.model;

import java.sql.Time;
import java.sql.Timestamp;

public class Period {
    private long id;
    private String name;
    private Timestamp from;
    private Timestamp to;

    public Period() {}
    public Period(String name, Timestamp from, Timestamp to) {
        this.name = name;
        this.from = from;
        this.to = to;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getFrom() {
        return from;
    }

    public void setFrom(Timestamp from) {
        this.from = from;
    }

    public Timestamp getTo() {
        return to;
    }

    public void setTo(Timestamp to) {
        this.to = to;
    }
}
