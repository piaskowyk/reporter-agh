package app.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ReportStatusHistoryTuple {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    private Date date;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;


    @Column(nullable = false)
    private ReportStatus reportStatus;

    ReportStatusHistoryTuple() {
    }

    ReportStatusHistoryTuple(User user, ReportStatus reportStatus) {
        this.user = user;
        this.reportStatus = reportStatus;
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }
}
