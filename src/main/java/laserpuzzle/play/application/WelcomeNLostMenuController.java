package laserpuzzle.play.application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

/**
 * Controls the welcome and the losing screen and opens or reopens the game itself.
 */
public class WelcomeNLostMenuController {

    /**
     * Reopens the game
     * @param event The event of clicking the "Yes button"
     * @throws IOException if it can't find game.fxml
     */
    @FXML
    private void switchScene(ActionEvent event) throws IOException {
        Logger.debug("Starting new game");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/game.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void closeGame() {
        Logger.debug("Exiting");
        Platform.exit();
    }

}
