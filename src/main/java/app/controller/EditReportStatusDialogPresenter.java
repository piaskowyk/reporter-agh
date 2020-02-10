package app.controller;

import app.entity.Report;
import app.entity.ReportStatus;
import app.entity.ReportStatusHistoryTuple;
import app.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class EditReportStatusDialogPresenter {

    private Stage dialogStage;
    private Report report;
    private User user;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private boolean approved = false;

    @FXML
    private VBox historyStatusContainer;

    @FXML
    private ChoiceBox<ReportStatus> reportStatusChoiceBox;

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }


    private void updateModel() {
        if (report.getCurrentReportStatus() != reportStatusChoiceBox.getValue()) {
            report.addReportStatus(reportStatusChoiceBox.getValue(), user);
        }

//        report.setReportStatus(reportStatusChoiceBox.getValue());
    }

    @FXML
    private void handleOkAction(ActionEvent event) {
        updateModel();
        approved = true;
        dialogStage.close();
    }

    public void setData(Report report, User user) {
        this.report = report;
        this.user = user;
        updateControls();
    }

    private void updateControls() {
        reportStatusChoiceBox.getItems().setAll(ReportStatus.values());
        reportStatusChoiceBox.setValue(this.report.getCurrentReportStatus());


        try {
            renderReportStatusHistory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void renderReportStatusHistory() throws IOException {
        historyStatusContainer.getChildren().clear();
        for (ReportStatusHistoryTuple reportTuple : report.getReportStatusHistory()) {
            HBox myLabel = FXMLLoader.load(getClass()
                    .getResource("/app/view/fxml/dialogs/ReportStatusHistoryLabel.fxml"));
            Label userNameLabel = (Label) myLabel.lookup("#userNameLabel");
            userNameLabel.setText(reportTuple.getUser().getName());


            Label statusNameLabel = (Label) myLabel.lookup("#statusNameLabel");
            statusNameLabel.setText(reportTuple.getReportStatus().toString());

            historyStatusContainer.getChildren().add(myLabel);
        }

    }

    public boolean isApproved() {
        return approved;
    }
}
