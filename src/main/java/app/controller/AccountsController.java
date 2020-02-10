package app.controller;

import app.Main;
import app.dao.UserDao;
import app.entity.User;
import app.viewTool.HasUserReference;
import app.viewTool.ViewOnloadEvent;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class AccountsController implements ViewOnloadEvent, HasUserReference {

    private UserDao userDao;

    private ObservableList<User> users;

    private User currentUser = null;

    @FXML
    private TableView<User> accountsTable;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> usertypeColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> surnameColumn;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;

    public void initialize() {
        userDao = new UserDao();
        users = userDao.getAllObservable();

        usernameColumn.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getLogin()));
        usertypeColumn.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getUserType().toString()));
        nameColumn.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getName()));
        surnameColumn.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getSurname()));

        accountsTable.setItems(users);

        ObservableValue<Boolean> emptySelection = Bindings.isEmpty(accountsTable.getSelectionModel().getSelectedItems());
        deleteButton.disableProperty().bind(emptySelection);
        editButton.disableProperty().bind(emptySelection);


    }
    private void showAlert(String title, String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    private boolean showTransactionEditDialog(User user) {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class
                    .getResource("/app/view/fxml/dialogs/UserEditDialog.fxml"));
            BorderPane page = (BorderPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Account details");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(accountsTable.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the presenter.
            UserEditDialogPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setData(user, currentUser);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return presenter.isApproved();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        User user = new User();
        if(showTransactionEditDialog(user)){
           Optional<User> newUser = userDao.create(
                   user.getName(),
                   user.getSurname(),
                   user.getLogin(),
                   user.getPassword(),
                   user.getUserType()
           );
        }
    }

    @FXML
    private void handleDeleteAction(ActionEvent event) {
        User user = accountsTable.getSelectionModel().getSelectedItem();
        if(user != null){
            if(user.getLogin().equals(currentUser.getLogin())){
                showAlert("Error", "You cannot delete your own account!");
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning: This action cannot be undone!");
            alert.setContentText("Are you sure you want to delete "+user.getLogin()+"?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                userDao.removeUser(user);
            }
        }
    }
    @FXML
    private void handleEditAction(ActionEvent event) {
        User user = accountsTable.getSelectionModel().getSelectedItem();
        if(user != null){
            if(showTransactionEditDialog(user)){
                userDao.updateUser(user);
            }
        }
    }

    @Override
    public void setUser(User user) {
        currentUser = user;
    }
}
