package app.viewTool;

import app.Main;

import app.entity.User;
import app.exception.IViewControllerNotImplementedException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ViewManager {

    private static Stage primaryStage;
    private static Map<String, Scene> allScene = new HashMap<>();
    private static Map<String, ViewOnloadEvent> allController = new HashMap<>();

    private static int sceneWidth = 1200;
    private static int sceneHeight = 800;

    private ViewManager() {}

    public static void init(Stage mainPrimaryStage) throws Exception {
        primaryStage = mainPrimaryStage;

        registerDefaultViews();

        primaryStage.setWidth(sceneWidth);
        primaryStage.setHeight(sceneHeight);

        primaryStage.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
            sceneWidth = newSceneWidth.intValue();
        });
        primaryStage.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
            sceneHeight = newSceneHeight.intValue();
        });
    }

    public static void clearViewData() {
        allController.clear();
    }

    public static void registerUserViews(User user) throws Exception {
        for (Views views : Views.values()) {
            if (user.getUserType().level >= views.permissionLevel)
                registerScene(views, user);
        }
    }

    private static void registerDefaultViews() throws Exception {
        for (Views views : Views.values()) {
            if (views.permissionLevel == 0) {
                registerScene(views);
            }

        }
    }

    public static void showView(Views view) {
        primaryStage.setScene(allScene.get(view.path));
        primaryStage.setTitle(view.title);
        primaryStage.show();
        primaryStage.setWidth(sceneWidth);
        primaryStage.setHeight(sceneHeight);

        // call event after change view
        allController.get(view.path).onLoad();
    }

    private static void registerScene(Views view) throws Exception {
        registerScene(view, null);
    }

    private static void registerScene(Views view, User user) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL path = Main.class.getResource("/app/view/fxml/" + view.path + ".fxml");
            if (path == null) {
                throw new Exception(view.path + " not found");
            }
            loader.setLocation(path);
            BorderPane root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    Main.class.getResource("/app/view/asset/style/style.css").toExternalForm()
            );

            if (!view.cssPath.equals("")) {
                scene.getStylesheets().add(
                        Main.class.getResource("/app/view/asset/style/" + view.cssPath + ".css")
                                .toExternalForm()
                );
            }
            allScene.put(view.path, scene);

            if (!(loader.getController() instanceof ViewOnloadEvent))
                throw new IViewControllerNotImplementedException(view.path);
            if (loader.getController() instanceof HasUserReference) {
                ((HasUserReference) loader.getController()).setUser(user);
            }
            Pane menuContainer = (Pane) root.lookup("#menuContainer");
            if(menuContainer != null) {
                MenuBuilder menuController = new MenuBuilder(menuContainer, user);
                menuController.generateUserMenu();
                System.out.println("jes");
            }

            allController.put(view.path, loader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}