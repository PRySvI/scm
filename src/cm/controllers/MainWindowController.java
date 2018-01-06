package cm.controllers;

import cm.start.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Created by Святослав on 04.01.2018.
 */
public class MainWindowController {

    Stage appStage;
    Main main;
    @FXML
    private void initialize(){

        appStage=Main.globalStage;

    }

    public void setMainApp(Main main) {
        this.main = main;
    }

    public void buttonMainListener(ActionEvent event) throws IOException {
        Object object = event.getSource();
        if(!(object instanceof Button))
            return;
        Button button = (Button)object;

        switch (button.getId())
        {
            case"clientCtrl":
             /*   Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../fxmls/control_clients_window.fxml")));
                appStage.setScene(scene);
                appStage.show();*/
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../fxmls/control_clients_window.fxml"));
                fxmlLoader.setResources(ResourceBundle.getBundle("cm.bundles.Locale", new Locale("fr")));
                Parent root = fxmlLoader.load();
                appStage.setTitle(fxmlLoader.getResources().getString("app_name"));
                appStage.setScene(new Scene(root));
                appStage.centerOnScreen();
                appStage.show();
                break;
        }
    }
}
