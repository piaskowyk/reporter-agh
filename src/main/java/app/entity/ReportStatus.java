package app.entity;

public enum ReportStatus {
    Pending("Pending"),
    InProgress("InProgress"),
    Done("Done"),
    Important("Important"),
    WaitingForInfo("WaitingForInfo"),
    Reject("Reject"),
    Cancel("Cancel");

    public final String name;

    ReportStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
