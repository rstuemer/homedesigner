package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Main extends Application {



    private static Stage primaryStage; // **Declare static Stage**

    private void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }

    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }
    @Override
    public void start(Stage primaryStage) throws Exception{

        Preferences prefs = Preferences.userRoot()
                .node("/HomeDesigner");



        try {
            String keys[] = prefs.keys();
            for (int i = 0, n = keys.length; i < n; i++) {
                System.out.println(keys[i] + ": " + prefs.getInt(keys[i], 0));
            }
        } catch (BackingStoreException e) {
            System.err.println("Unable to read backing store: " + e);
        }

        setUserAgentStylesheet(STYLESHEET_MODENA);
        setPrimaryStage(primaryStage); // **Set the Stage**

        Parent root = FXMLLoader.load(getClass().getResource("../mainView.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root);
       String cssFile =  getClass().getResource("../styles/dark.css").toExternalForm();
        scene.getStylesheets().add(cssFile);


        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();




    }


    public static void main(String[] args) {
        launch(args);


    }
}
