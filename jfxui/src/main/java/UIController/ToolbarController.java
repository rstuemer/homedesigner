package UIController;

import de.rst.core.EditState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javax.inject.Inject;

public class ToolbarController implements IController {



    @Inject
    AppState appState;

    @FXML
    public void enterPickMode(ActionEvent event) {
//        appState.setEditMode("pickMode");
//        appState.setCurrentWall(null);
//        appState.getProject().setUpdateView2d(true);

        //initView2d(view2D);
    }


    @FXML
    public void enterCreateWallMode(ActionEvent event) {
        event.consume();

        EditState value = appState.getEditState().getValue();
        if (!value.equals("START_CREATION")) {
            value.changeCurrentState("START_CREATION");
            appState.getEditState().setValue(value);
        } else {
            value.changeCurrentState("NONE");
            appState.getEditState().setValue(value);
        }
    }

    @Override
    public void initView() {

    }
}
