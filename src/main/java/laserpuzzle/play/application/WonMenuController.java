package laserpuzzle.play.application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import laserpuzzle.play.resultmanager.GameResult;
import laserpuzzle.play.resultmanager.JsonGameResultManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.tinylog.Logger;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Controls the winning screen and reopens the game itself.
 */
public class WonMenuController {

    @FXML
    private TableView<GameResult> tableView;

    @FXML
    private TableColumn<GameResult, String> playerName;

    @FXML
    private TableColumn<GameResult, Integer> steps;

    /**
     * Displays the best 5 players.
     * @throws IOException if it can't find results.json
     */
    @FXML
    private void initialize() throws IOException {
        playerName.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        steps.setCellValueFactory(new PropertyValueFactory<>("steps"));
        ObservableList<GameResult> observableList = FXCollections.observableArrayList();
        observableList.addAll(new JsonGameResultManager(Path.of("results.json")).getBest(5));
        tableView.setItems(observableList);
    }

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
