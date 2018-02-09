package UIController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javax.inject.Inject;

public class ToolbarController {



    @Inject
    AppState appState;

    @FXML
    public void enterPickMode(ActionEvent event) {
        appState.setEditMode("pickMode");
        appState.setCurrentWall(null);
        appState.getProject().setUpdateView2d(true);

        //initView2d(view2D);
    }


    @FXML
    public void enterCreateWallMode(ActionEvent event) {
        event.consume();

        if (!appState.getEditMode().equals("create_wall")) {
            appState.setEditMode("create_wall");
        } else {
            appState.setEditMode("none");
        }
    }
}
