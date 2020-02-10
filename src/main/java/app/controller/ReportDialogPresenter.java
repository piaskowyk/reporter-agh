package app.controller;

import app.dao.MessageDao;
import app.entity.FormField;
import app.entity.Message;
import app.entity.Report;
import app.entity.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;


public class ReportDialogPresenter {

    private Report report;
    private Stage dialogStage;
    private User currentUser;
    void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
    private MessageDao messageDao = new MessageDao();

    @FXML
    private TextField messageField;

    @FXML
    private TableView<Message> messagesTable;

    @FXML
    private TableColumn<Message, String> dateColumn;
    @FXML
    private TableColumn<Message, String> nameColumn;
    @FXML
    private TableColumn<Message, String> surnameColumn;
    @FXML
    private TableColumn<Message, String> messageColumn;

    @FXML
    private Label reportTypeLabel;

    @FXML
    private Label reportDateLabel;

    @FXML
    private Label reportAuthorLabel;

    @FXML
    private VBox reportContentContainer;

    @FXML
    private VBox messageContainer;

    @FXML
    private Button sendButton;


    private ObservableList<Message> messages;

    private boolean reportContentIsRender = false;

    @FXML
    private void handleSendAction(ActionEvent event) {
        messageDao.create(currentUser,report, messageField.getText());
        messageField.setText("");
        updateControls();
    }

    void setData(Report report, User currentUser) {
        this.report = report;
        this.currentUser = currentUser;
        sendButton.setDefaultButton(true);
        updateControls();
    }

    private void updateControls() {
        if(!reportContentIsRender) {
            renderReportContent();
            reportContentIsRender = true;
        }

        renderMessages();
    }

    private void renderReportContent() {
        reportTypeLabel.setText("Type: " + report.getReportType().getName());
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        reportDateLabel.setText(format.format(report.getDate()));
        reportAuthorLabel.setText("Author: " + report.getUser().getName() + " " + report.getUser().getSurname());

        int fieldIndex = 0;
        for(FormField formField : report.getFormFields()) {
            Label title = new Label();
            title.setPrefHeight(30);
            title.setText(report.getReportType().getFormFieldsPattern().get(fieldIndex).getDescription() + ":");
            Label content = new Label();
            content.setText(formField.getContent());
            reportContentContainer.getChildren().add(title);
            reportContentContainer.getChildren().add(content);
            fieldIndex++;
        }
    }

    private void renderMessages() {
        messageContainer.getChildren().clear();

        for(Message message : report.getMessages()) {
            Label author = new Label();
            author.setPrefHeight(30);
            author.setAlignment(Pos.BOTTOM_LEFT);
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            author.setText(message.getUser().getName()
                    + " " + message.getUser().getSurname()
                    + " | " + format.format(message.getDate()));

            Label messageText = new Label();
            messageText.setPrefHeight(20);
            messageText.setAlignment(Pos.BOTTOM_LEFT);
            messageText.setText(message.getMessage());

            messageContainer.getChildren().add(author);
            messageContainer.getChildren().add(messageText);
        }
    }
}
