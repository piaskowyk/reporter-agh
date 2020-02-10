package app.controller;

import app.security.AuthorizationProvider;
import app.security.UserAuthorization;
import app.security.Validator;
import app.viewTool.ViewOnloadEvent;
import app.viewTool.ViewManager;
import app.viewTool.Views;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController implements ViewOnloadEvent {
    @FXML
    private Button loginBtn;

    @FXML
    private Label statusText;

    @FXML
    private TextField loginInput, passwordInput;

    public void initialize() {
        loginBtn.setDefaultButton(true);
        loginBtn.setOnAction(e -> {
            if (!valid()) return;

            statusText.setText("Waiting...");
            AuthorizationProvider authorizationProvider = new UserAuthorization();
            authorizationProvider.authorize(loginInput.getText(), passwordInput.getText());

            if (!authorizationProvider.isAuthorized()) {
                statusText.setText("Login failed. Try again.");
            } else {
                try {
                    ViewManager.registerUserViews(authorizationProvider.getUser());
                    ViewManager.showView(Views.MainView);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to load user view");
                }
            }
        });

        //TEST
        loginInput.setText("studek@agh.edu.pl");
        passwordInput.setText("passwd");
    }


    private boolean valid() {
        StringBuilder statusList = new StringBuilder();
        boolean isValid = true;

        if (!Validator.isValidEmail(loginInput.getText())) {
            statusList.append("Invalid email. ");
            isValid = false;
        }

        if (passwordInput.getText().length() > 40) {
            statusList.append("Password is too long. ");
            isValid = false;
        }

        if (statusList.length() > 0) {
            statusText.setText(statusList.toString());
            isValid = false;
        }

        return isValid;
    }
}
