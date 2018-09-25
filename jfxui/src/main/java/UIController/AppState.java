package UIController;

import com.google.inject.Singleton;

import de.rst.core.EditState;
import de.rst.core.Plan;
import de.rst.core.Project;
import de.rst.core.Wall;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.stage.Stage;
import services.Settings;


@Singleton
public class AppState {


    private final ObjectProperty<Cursor> cursor = new SimpleObjectProperty<>(Cursor.DEFAULT);

    private Settings settings;

    private SimpleObjectProperty<EditState> editState ;

    private SimpleObjectProperty<Project> project;

    private Wall currentWall;

    private Stage primaryStage;
    private DoubleProperty zoomProperty;


    public AppState() {
        project = new SimpleObjectProperty<>();
        zoomProperty = new SimpleDoubleProperty();
        editState  = new SimpleObjectProperty<>();
    }


    public SimpleObjectProperty<EditState> getEditState(){

        if(this.editState == null) {
            this.editState  = new SimpleObjectProperty<>();
            EditState v = new EditState();
            this.editState.setValue(v);
        }
        return this.editState;
    }


    public void setEditState(EditState state){
        this.editState.set(state);
    }

    public boolean changeEditState(String newState){
       return  this.editState.get().changeCurrentState(newState);
    }




    public Project getProject() {
        return project.get();
    }


    public void setProject(Project project) {
        this.project.set(project);
        zoomProperty.set(project.getPlan().getZoom());
    }

    public SimpleObjectProperty<Project> projectProperty() {
        return project;
    }

    public DoubleProperty getZoomProperty() {
        return this.zoomProperty;
    }

    public void setZoom(double zoom){
        this.zoomProperty.setValue(zoom);
        Plan plan = this.getProject().getPlan();
        plan.setZoom(zoom);
        this.getProject().setPlan(plan);
    }




    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ObjectProperty<Cursor> getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor.set(cursor);
    }
}
