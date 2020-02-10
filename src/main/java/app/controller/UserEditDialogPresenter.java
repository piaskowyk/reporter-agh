package app.controller;

import app.entity.User;
import app.entity.UserType;
import app.security.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class UserEditDialogPresenter {

    private Stage dialogStage;
    private User user;
    private User currentUser;
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
    private boolean approved = false;

    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private ChoiceBox<UserType> accounttypeChoiceBox;

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }

    private void showAlert(String title, String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);

        alert.showAndWait();
    }
    private boolean checkIfEmptyField(TextField textField, String name){
        if(textField.getText() == null || textField.getText().length() == 0){
            showAlert("Error", name+" field cannot be empty");
            return true;
        }
        return false;
    }
    private boolean isValid(){
        //TODO
        if(checkIfEmptyField(loginTextField, "Login") ||
                checkIfEmptyField(nameTextField, "Name") ||
                checkIfEmptyField(surnameTextField, "Surname") ||
                checkIfEmptyField(passwordField, "Password")){
            return false;
        }
        if(accounttypeChoiceBox.getValue() == null){
            showAlert("Error", "User type field cannot be empty");
            return false;
        }
        if(!Validator.isValidEmail(loginTextField.getText())){

            showAlert("Error", "Invalid e-mail");
            return false;
        }

        if(user.getLogin() != null && user.getLogin().equals(currentUser.getLogin())){
            if(accounttypeChoiceBox.getValue() != currentUser.getUserType()){
                showAlert("Error", "You cannot revoke your own administrator privileges!");
                accounttypeChoiceBox.setValue(currentUser.getUserType());
                return false;
            }
        }
        return true;
    }
    private void updateModel(){
        user.setName(nameTextField.getText());
        user.setSurname(surnameTextField.getText());
        user.setLogin(loginTextField.getText());
        user.setUserType(accounttypeChoiceBox.getValue());
        user.setPassword(passwordField.getText());
    }
    @FXML
    private void handleOkAction(ActionEvent event) {

        if(isValid()){
            updateModel();
            approved = true;
            dialogStage.close();
        }
    }

    public void setData(User user, User currentUser){
        this.user = user;
        this.currentUser = currentUser;
        updateControls();
    }

    private void updateControls(){
        loginTextField.setText(user.getLogin());
        nameTextField.setText(user.getName());
        surnameTextField.setText(user.getSurname());
        accounttypeChoiceBox.getItems().setAll(UserType.values());
        accounttypeChoiceBox.setValue(user.getUserType());
        passwordField.setText(user.getPassword());
        //accounttypeChoiceBox.setItems(FXCollections.observableArrayList(UserType.values()));
        //accounttypeChoiceBox.setSelectionModel();
    }
    public boolean isApproved(){
        return approved;
    }
}
