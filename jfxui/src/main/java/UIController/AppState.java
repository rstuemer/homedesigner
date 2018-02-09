package UIController;

import com.google.inject.Singleton;

import de.rst.core.Project;
import de.rst.core.Wall;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Stage;
import services.Settings;


@Singleton
public class AppState {



    private Settings settings;

    private  String editMode = "none";

    private SimpleObjectProperty<Project> project;

    private Wall currentWall;

    private Stage primaryStage;



    public AppState(){
        project = new SimpleObjectProperty<>();
    }

    public String getEditMode() {
        return editMode;
    }

    public void setEditMode(String editMode) {
        this.editMode = editMode;
    }

    public Project getProject() {
        return project.get();
    }


    public void setProject(Project project) {
        this.project.set(project);
    }

    public SimpleObjectProperty<Project> projectProperty() {
        return project;
    }

    public Wall getCurrentWall() {
        return currentWall;
    }

    public void setCurrentWall(Wall currentWall) {
        this.currentWall = currentWall;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings){
            this.settings = settings;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
