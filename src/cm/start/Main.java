package cm.start;

import cm.controllers.Controller;
import cm.controllers.MainWindowController;
import cm.daemons.AniversaryChecker;
import cm.interfaces.impls.PersonneMenagerEngine;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    public static Stage globalStage;
    public static Scene mainScene;
    public static PersonneMenagerEngine personneMenagerEngine;
    @Override
    public void start(Stage primaryStage) throws Exception{
        globalStage=primaryStage;

        personneMenagerEngine = new PersonneMenagerEngine();
        FXMLLoader fxmlLoader = new FXMLLoader();
        //fxmlLoader.setLocation(getClass().getResource("../fxmls/control_clients_window.fxml"));
        fxmlLoader.setLocation(getClass().getResource("../fxmls/mainWindow.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("cm.bundles.Locale", new Locale("fr")));
        Parent root = fxmlLoader.load();
        MainWindowController controller = fxmlLoader.getController();
        controller.setMainApp(this);
        primaryStage.setTitle(fxmlLoader.getResources().getString("app_name"));
        primaryStage.setScene(new Scene(root));
        mainScene = primaryStage.getScene();
        //primaryStage.getIcons().add(new Image("file:resources/images/iconfinder-32.png"));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../resources/images/iconfinder-32.png")));
        primaryStage.show();
        new AniversaryChecker().start();

    }
    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
