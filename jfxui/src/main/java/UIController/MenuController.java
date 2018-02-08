package UIController;

import de.rst.core.Project;
import de.rst.core.guice.modules.InjectLogger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import org.slf4j.Logger;
import sample.Main;

import java.io.File;
import java.util.ResourceBundle;

public class MenuController implements Initializable {


    @FXML
    private MenuBar menuBar;


    //TODO CDI:
    private Project project;


    @InjectLogger
    Logger logger;

    /**
     * Handle action related to "About" menu item.
     *
     * @param event Event on "About" menu item.
     */
    @FXML
    private void handleSaveAction(final ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //TODO CDI: REPLACE WITH CDI INJECTION of primaryStage
        //Show save file dialog
        File file = fileChooser.showSaveDialog(Main.getPrimaryStage());
        logger.info("Save to file:" + file.getName());
        if(file != null){
            project.saveProject(file);
        }

    }



    /**
     * Handle action related to "About" menu item.
     *
     * @param event Event on "About" menu item.
     */
    @FXML
    private void handleAboutAction(final ActionEvent event)
    {
        provideAboutFunctionality();
    }

    /**
     * Handle action related to input (in this case specifically only responds to
     * keyboard event CTRL-A).
     *
     * @param event Input event.
     */
    @FXML
    private void handleKeyInput(final InputEvent event)
    {
        if (event instanceof KeyEvent)
        {
            final KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.A)
            {
                provideAboutFunctionality();
            }
        }
    }

    /**
     * Perform functionality associated with "About" menu selection or CTRL-A.
     */
    private void provideAboutFunctionality()
    {
        System.out.println("You clicked on About!");
    }


    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        menuBar.setFocusTraversable(true);

    }


    public void foo(String foo) {
        System.out.println(foo);
    }


    public void setProject(Project project) {
        this.project = project;
    }

    public void handleNewAction(ActionEvent event) {


        project = new Project();
        project.init();

    }

    public void handleOpenAction(ActionEvent event) {
    }

    public void handleSaveAsAction(ActionEvent event) {
    }
}
