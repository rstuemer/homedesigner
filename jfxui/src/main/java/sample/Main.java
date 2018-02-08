package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

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
