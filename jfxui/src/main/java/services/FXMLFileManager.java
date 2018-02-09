package services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javax.inject.Inject;

public interface  FXMLFileManager {

      Parent load(String viewName);
}
