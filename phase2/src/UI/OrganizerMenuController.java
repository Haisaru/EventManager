package UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
/**
 * A controller for the menu layout of an Organizer user.
 */
public class OrganizerMenuController extends MenuController{
    @FXML
    Button createButton;

    /**
     * Opens up a new scene/stage for organizer to create new users.
     * Returns back to this scene when done.
     * @param event an event denoting the user's clicking action.
     */
    public void createButtonOnAction(ActionEvent event){
        //TODO:
    }
}