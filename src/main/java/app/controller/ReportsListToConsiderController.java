package app.controller;

import app.Main;
import app.dao.ReportDao;
import app.entity.Report;
import app.entity.ReportStatus;
import app.entity.User;
import app.viewTool.HasUserReference;
import app.viewTool.ViewOnloadEvent;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportsListToConsiderController implements ViewOnloadEvent, HasUserReference {

    private ReportDao reportDao;

    @FXML
    private TableView<Report> allReportsTable;
    @FXML
    private TableColumn<Report, Date> dateColumn;
    @FXML
    private TableColumn<Report, String> reportTypeColumn;
    @FXML
    private TableColumn<Report, String> firstMessageColumn;
    @FXML
    private TableColumn<Report, String> userNameColumn;
    @FXML
    private TableColumn<Report, String> userSurnameColumn;
    @FXML
    private TableColumn<Report, String> reportStatusColumn;
    @FXML
    private ChoiceBox<ReportStatus> reportStatusChoiceBox;
    @FXML
    private Button allButton;

    private User user;


    public void initialize() {
        reportDao = new ReportDao();

        reportTypeColumn.setCellValueFactory(
                report -> new SimpleStringProperty(report.getValue().getReportType().getName())
        );

        initializeDateColumn();

        firstMessageColumn.setCellValueFactory(
                report -> new SimpleStringProperty(report.getValue().getFormFields().get(0).getContent())
        );
        initializeUserNamesColumn();

        reportStatusColumn.setCellValueFactory(
                report -> new SimpleStringProperty(report.getValue().getCurrentReportStatus().toString())
        );

        initializeReportsTable();

        initializeReportStatusChoiceBox();

        allButton.disableProperty().bind(Bindings.isNull(reportStatusChoiceBox.getSelectionModel().selectedItemProperty()));
    }

    private void initializeReportStatusChoiceBox() {
        reportStatusChoiceBox.setItems(FXCollections.observableArrayList(ReportStatus.values()));

        reportStatusChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, previous, current) -> {
            if (current == null) {
                allReportsTable.setItems(reportDao.getAllObservable());
            } else {
                allReportsTable.setItems(reportDao.getWithStatusObservable(current));
            }
        });
    }

    private void initializeReportsTable() {
        allReportsTable.setItems(reportDao.getAllObservable());

        allReportsTable.setRowFactory(allReportsTableView -> {
            TableRow<Report> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Report rowData = row.getItem();
                    showReportEditDialog(rowData);
                }
            });
            return row;
        });
    }

    private void initializeUserNamesColumn() {
        userNameColumn.setCellValueFactory(
                report -> new SimpleStringProperty(report.getValue().getUser().getName())
        );
        userSurnameColumn.setCellValueFactory(
                report -> new SimpleStringProperty(report.getValue().getUser().getSurname())
        );
    }


    private void initializeDateColumn() {
        dateColumn.setCellValueFactory(report -> new SimpleObjectProperty<>(report.getValue().getDate()));
        dateColumn.setCellFactory(report -> new TableCell<Report, Date>() {
            private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    this.setText(format.format(item));
                }
            }
        });
    }


    @FXML
    private void editReportStatus(ActionEvent event) {
        Report report = allReportsTable.getSelectionModel().getSelectedItem();
        if (report != null) {
            if (showTransactionEditDialog(report)) {
                reportDao.updateReport(report);
            }
        }
    }

    @FXML
    private void handleAllAction(ActionEvent event) {
        reportStatusChoiceBox.setValue(null);
    }

    private void showReportEditDialog(Report report) {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class
                    .getResource("/app/view/fxml/dialogs/ReportDialog.fxml"));
            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Report details");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(allReportsTable.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the presenter.
            ReportDialogPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setData(report, user);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean showTransactionEditDialog(Report report) {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class
                    .getResource("/app/view/fxml/dialogs/EditReportStatus.fxml"));
            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Report Status");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(allReportsTable.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the presenter.
            EditReportStatusDialogPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setData(report, user);

            dialogStage.showAndWait();
            return presenter.isApproved();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onLoad() {
        reportDao.reloadObservableList();
    }


    @Override
    public void setUser(User user) {
        this.user = user;
    }
}
