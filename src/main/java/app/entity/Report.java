package app.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "report_type")
    private ReportType reportType;

    @OneToMany(cascade = CascadeType.ALL)
    private List<FormField> formFields;

//    @Column(nullable = false)
//    private ReportStatus reportStatus;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<ReportStatusHistoryTuple> reportStatusHistory;

    @OneToMany
    private List<Message> messages;

    public Report(User user, ReportType reportType) {
        date = new Date();
        this.user = user;
        this.reportType = reportType;
        reportStatusHistory = new ArrayList<>();
        reportStatusHistory.add(new ReportStatusHistoryTuple(user, ReportStatus.Pending));
//        reportStatus = ReportStatus.Pending;
        this.messages = new ArrayList<>();
    }

    public Report(User user, ReportType reportType, List<FormField> formFields) {
        date = new Date();
        this.user = user;
        this.reportType = reportType;
        this.formFields = formFields;
        reportStatusHistory = new ArrayList<>();
        reportStatusHistory.add(new ReportStatusHistoryTuple(user, ReportStatus.Pending));
//        reportStatus = ReportStatus.Pending;
        this.messages = new ArrayList<>();
    }

    public Report() {
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public List<FormField> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormField> formFields) {
        this.formFields = formFields;
    }

    public List<ReportStatusHistoryTuple> getReportStatusHistory() {
        return reportStatusHistory;
    }

    //    public void setReportStatus(ReportStatus reportStatus) {
//        this.reportStatus = reportStatus;
//    }
    public void addReportStatus(ReportStatus reportStatus, User user) {
        this.reportStatusHistory.add(new ReportStatusHistoryTuple(user, reportStatus));
    }

    public ReportStatus getCurrentReportStatus() {
        if (reportStatusHistory.size() == 0) {
            throw new RuntimeException("No report status history");
        }
        return reportStatusHistory.get(reportStatusHistory.size() - 1).getReportStatus();
    }

    public void addMessage(Message message) {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        this.messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }
}
