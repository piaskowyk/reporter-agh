package app.controller;

import app.dao.ReportDao;
import app.entity.ReportType;
import app.entity.User;
import app.viewTool.HasUserReference;
import app.viewTool.ViewOnloadEvent;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

public class AddReportController implements ViewOnloadEvent, HasUserReference {

    @FXML
    private ChoiceBox reportTypeSelect;

    @FXML
    private VBox container;

    @FXML
    private Pane formContainer;

    @FXML
    private Button addReportBtn;

    private ReportDao reportDao = new ReportDao();
    private User user = null;

    public void initialize() {
        addReportBtn.disableProperty().bind(reportTypeSelect.getSelectionModel().selectedItemProperty().isNull());

        final List<ReportType> reportTypes;
        Optional<List<ReportType>> reportTypeList = reportDao.getReportTypeList();
        if(reportTypeList.isPresent()) {
            reportTypes = reportTypeList.get();

            for(ReportType item : reportTypes) {
                reportTypeSelect.getItems().add(item.getName());
            }

            reportTypeSelect.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    reportDao.setReportType(reportTypes.get(new_val.intValue()));
                    createForm();
            });
        }

        addReportBtn.setOnAction(e -> {
            if(!reportDao.formIsValid()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Incorrect data.");
                alert.show();
                return;
            }

            boolean success = true;
            try {
                reportDao.addNewReport(user);
            } catch (Exception ex) {
                ex.printStackTrace();
                success = false;
                Alert alert = new Alert(Alert.AlertType.ERROR, "Unable save to database.");
                alert.show();
            }

            if(success) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Add new report.");
                alert.show();
            }
        });

    }

    private void createForm() {
        formContainer.getChildren().clear();
        formContainer.getChildren().add(reportDao.getForm());
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }
}
