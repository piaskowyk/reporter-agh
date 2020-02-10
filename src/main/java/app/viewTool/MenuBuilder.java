package app.viewTool;

import app.entity.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MenuBuilder {

    private VBox menuBar;
    private Pane menuContainer;
    private User user;

    public MenuBuilder(Pane menuContainer, User user) {
        this.menuContainer = menuContainer;
        this.user = user;
        menuBar = (VBox) menuContainer.lookup("#menuBar");
    }

    public void generateUserMenu() throws IOException {
        for (Views view : Views.values()) {
            if (view.permissionLevel <= user.getUserType().level && view.permissionLevel != 0) {
                Pane myButton = FXMLLoader.load(getClass()
                        .getResource("/app/view/fxml/components/menuButton.fxml"));
                Label label = (Label) myButton.lookup("#label");
                label.setText(view.title);
                myButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> ViewManager.showView(view));
                menuBar.getChildren().add(myButton);
            }
        }
        Pane logoutButton = FXMLLoader.load(getClass()
                .getResource("/app/view/fxml/components/menuButton.fxml"));
        Label label = (Label) logoutButton.lookup("#label");
        label.setText("Logout");
        logoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            ViewManager.showView(Views.Login);
        });
        menuBar.getChildren().add(logoutButton);
    }
}
