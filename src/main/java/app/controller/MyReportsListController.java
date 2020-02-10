package app.controller;

import app.Main;
import app.dao.ReportDao;
import app.entity.Report;
import app.entity.User;
import app.viewTool.HasUserReference;
import app.viewTool.ViewOnloadEvent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyReportsListController implements ViewOnloadEvent, HasUserReference {
    private ReportDao reportDao;

    @FXML
    private TableView<Report> myReportsTable;
    @FXML
    private TableColumn<Report, Date> dateColumn;
    @FXML
    private TableColumn<Report, String> reportTypeColumn;
    @FXML
    private TableColumn<Report, String> firstMessageColumn;
    @FXML
    private TableColumn<Report, String> reportStatusColumn;

    private User user;

    public void initialize() {
        reportDao = new ReportDao();

        reportTypeColumn.setCellValueFactory(
                report -> new SimpleStringProperty(report.getValue().getReportType().getName())
        );

        dateColumn.setCellValueFactory(report -> new SimpleObjectProperty<>(report.getValue().getDate()));
        dateColumn.setCellFactory(report -> new TableCell<Report, Date>() {
            private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    this.setText(format.format(item));
                }
            }
        });

        firstMessageColumn.setCellValueFactory(
                report -> new SimpleStringProperty(report.getValue().getFormFields().get(0).getContent())
        );

        reportStatusColumn.setCellValueFactory(
                report -> new SimpleStringProperty(report.getValue().getCurrentReportStatus().toString())
        );

        myReportsTable.setRowFactory(reportTableView -> {
            TableRow<Report> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Report rowData = row.getItem();
                    showReportEditDialog(rowData);
                }
            });
            return row ;
        });

    }

    private void showReportEditDialog(Report report) {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class
                    .getResource("/app/view/fxml/dialogs/ReportDialog.fxml"));
            BorderPane page = (BorderPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Report details");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(myReportsTable.getScene().getWindow());
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

    @Override
    public void onLoad() {
        reportDao.reloadObservableList();
    }

    @Override
    public void setUser(User user) {
        this.user = user;
        myReportsTable.setItems(reportDao.getAllForUserObservable(user));
    }
}
