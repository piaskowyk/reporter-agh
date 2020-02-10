package app.dao;

import app.entity.*;
import app.form.FormBuilder;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

import javax.persistence.PersistenceException;
import java.util.*;
import java.util.stream.Collectors;

public class ReportDao extends GenericDao<Report> {

    private ObservableList<Report> observableReports = null;
    private ObservableList<Report> observableReportsWithStatus = null;

    private ObjectProperty<ReportType> reportType;
    private List<String> valueList;

    private ReportTypeDao reportTypeDao = new ReportTypeDao();
    private FormBuilder formBuilder;

    public ReportDao() {
        this.reportType = new SimpleObjectProperty<>(null);
    }

    public Optional<Report> add(User user, ReportType reportType, List<String> valueList) throws Exception {
        if (reportType.getFormFieldsPattern().size() != valueList.size()) {
            throw new Exception("Incorrect count of parameters");
        }

        Report report = new Report(user, reportType);

        List<FormField> formFields = new ArrayList<>();
        int index = 0;

        for (FormFieldPattern fieldPattern : reportType.getFormFieldsPattern()) {
            FormField formField = new FormField(fieldPattern.getFormFieldType(), valueList.get(index));
            formField.setReport(report);
            formFields.add(formField);
        }
        report.setFormFields(formFields);

        try {
            save(report);
        } catch (PersistenceException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(report);
    }

    public boolean updateReport(Report report) {
        try {
            update(report);
            if (observableReports != null)
                observableReports.set(observableReports.indexOf(report), report);
            return true;
        } catch (PersistenceException e) {
            return false;
        }
    }


    public Optional<List<Report>> getAll() {
        try {
            List<Report> reportList = currentSession().createQuery(
                    "SELECT r FROM Report r",
                    Report.class
            ).getResultList();
            return Optional.of(reportList);
        } catch (PersistenceException e) {
            return Optional.empty();
        }
    }

    public Optional<List<Report>> getAllWithStatus(ReportStatus reportStatus) {
        try {
//            List<Report> reportList = currentSession().createQuery(
//                    "SELECT r FROM Report r join  ReportStatusHistoryTuple rs on rs. WHERE  r.reportStatus = :status",
//                    Report.class
//            )
//                    .setParameter("status", reportStatus)
//                    .getResultList();
//
            Optional<List<Report>> reportList = getAll();
            List<Report> filteredReports = reportList
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(report -> report.getCurrentReportStatus() == reportStatus)
                    .collect(Collectors.toList());



            return Optional.of(filteredReports);


//            return Optional.of(reportList);
        } catch (PersistenceException e) {
            return Optional.empty();
        }
    }


    public ObservableList<Report> getAllObservable() {
        if (observableReports == null) {
            this.observableReports = FXCollections.observableArrayList();
            reloadObservableList();
        }
        return observableReports;
    }

    public ObservableList<Report> getWithStatusObservable(ReportStatus reportStatus) {
        this.observableReportsWithStatus = FXCollections.observableArrayList();
        reloadObservableListWithStatus(reportStatus);
        return observableReportsWithStatus;
    }

    public ObservableList<Report> reloadObservableList() {
        this.observableReports.setAll(getAll().map(FXCollections::observableList)
                .orElseGet(FXCollections::emptyObservableList));
        return this.observableReports;
    }

    public ObservableList<Report> reloadObservableListWithStatus(ReportStatus reportStatus) {
        this.observableReportsWithStatus.setAll(getAllWithStatus(reportStatus).map(FXCollections::observableList)
                .orElseGet(FXCollections::emptyObservableList));
        return this.observableReportsWithStatus;
    }

    public ObservableList<Report> getAllForUserObservable(User user) {
        ObservableList<Report> list = getAllObservable();
        return list.filtered(report -> {
            if (report.getUser() != null) return report.getUser().equals(user);
            return false;
        });
    }

    public boolean removeReport(Report report) {
        try {
            remove(report);
            return true;
        } catch (PersistenceException e) {
            return false;
        }
    }

    public void setReportType(ReportType reportType) {
        this.reportType.set(reportType);
    }

    public void addNewReport(User user) throws Exception {
        valueList = formBuilder.getFormData();
        add(user, reportType.getValue(), valueList);
    }

    public Optional<List<ReportType>> getReportTypeList() {
        return reportTypeDao.getAll();
    }

    public Pane getForm() {
        formBuilder = new FormBuilder(reportType.get());
        return formBuilder.getForm();
    }

    public boolean formIsValid() {
        if (formBuilder != null) return true;
        return false;
    }

}
