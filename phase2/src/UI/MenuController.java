package UI;
import entities.User;
import gateways.LoginGateway;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import use_cases.UserManager;

import javafx.event.ActionEvent;

import java.io.IOException;

abstract class MenuController extends GeneralController{
    @FXML
    Button seeSignedUpButton;
    @FXML
    protected Button browseButton;


    public void seeSignedUpButtonOnAction(ActionEvent event){
        //TODO: Implement this, you can get the current user by using mainModel.getCurrentUser. Open different type of
        //TODO: scene depending on the user's type.
    }

    /**
     * Opens up a new scene/stage for the user to browse events available.
     * Scene/stage opened will vary depending on the type of user.
     * Returns back to this scene when done.
     * @param event an event denoting the user's clicking action.
     */
    public void browseButtonOnAction(ActionEvent event){
        //TODO: Implement this, you can get the current user by using mainModel.getCurrentUser. Open different type of
        //TODO: scene depending on the user's type.
    }
}
