package laserpuzzle.play.application;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import laserpuzzle.model.Direction;
import laserpuzzle.model.PuzzleState;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import laserpuzzle.play.resultmanager.GameResult;
import laserpuzzle.play.resultmanager.GameResultManager;
import laserpuzzle.play.resultmanager.JsonGameResultManager;

import org.tinylog.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Controls the game itself, makes the UI playable.
 */
public class GameController {

    @FXML
    private GridPane board;
    @FXML
    private GridPane cannonCols;
    @FXML
    private GridPane cannonRows;
    @FXML
    private Button upButton;
    @FXML
    private Button rightButton;
    @FXML
    private Button downButton;
    @FXML
    private Button leftButton;

    @FXML
    private TextField playerName;

    private int numberOfSteps=0;

    private static final int[] evenCannonCols=new int[]{1, 4, 8, 10, 12};
    private static final int[] oddCannonCols=new int[]{2, 7};
    private static final int[] evenCannonRows=new int[]{2, 6, 8, 10, 12};
    private static final int[] oddCannonRows=new int[]{1, 4, 7, 11};

    private PuzzleState game = new PuzzleState();

    private GameResultManager manager = new JsonGameResultManager(Path.of("results.json"));

    /**
     * Builds the board and adds an empty array to the JSON that stores the players' data.
     * @throws IOException
     */
    @FXML
    private void initialize() throws IOException {
        File jsonFile = new File(String.valueOf(Path.of("results.json")));
        if(jsonFile.length() == 0){
            FileWriter fileWriter = new FileWriter("results.json");
            fileWriter.write("[]");
            fileWriter.close();

        }

        initializeCannonCols();
        initializeCannonRows();
        initializeBoard();
        registerKeyEventHandler();
    }
    private void initializeCannonCols(){
        for (var j = 0; j < cannonCols.getColumnCount(); j++) {
            var square = createCannonColSquare(j);
            cannonCols.add(square, j, 0);
        }
    }
    private StackPane createCannonColSquare(int j) {
        var cannonVariables=setCannonVariables(true);

        return setCannon(j, cannonVariables);
    }
    private void initializeCannonRows(){
        for (var i = 0; i < cannonRows.getRowCount(); i++) {
            var square = createCannonRowSquare(i);
            cannonRows.add(square, 0, i);
        }
    }
    private StackPane createCannonRowSquare(int j) {
        var cannonVariables=setCannonVariables(false);

        return setCannon(j, cannonVariables);
    }

    /**
     * {@return all the variables in an object that the setCannon method needs as parameters}
     * @param cols whether the method is called for a column /if false it's called for a row/ to determine which pictures to use
     */
    private CannonVariables setCannonVariables(boolean cols){
        var isCannon=false;
        var empty=new Circle(0);

        var square = new StackPane();
        square.getStyleClass().add("square");

        var cannon = new ImageView();
        cannon.setFitWidth(25);
        cannon.setFitHeight(25);
        if(cols){
            return new CannonVariables(isCannon, empty, square, cannon,
                    new Image("activeCol.png"),
                    new Image("inactiveCol.png"),
                    evenCannonCols, oddCannonCols);
        }
        else{
            return new CannonVariables(isCannon, empty, square, cannon,
                    new Image("activeRow.png"),
                    new Image("inactiveRow.png"),
                    evenCannonRows, oddCannonRows);
        }
    }

    /**
     * {@return the {@code StackPane} with the right image}
     * @param j the current row or column
     * @param cannonVariables object containing the necessary variables to determine and add the right image
     */
    private StackPane setCannon(int j, CannonVariables cannonVariables) {
        addCannon(j, cannonVariables);

        if(cannonVariables.isCannon()){
            cannonVariables.getSquare().getChildren().add(cannonVariables.getCannon());
        }
        else{
            cannonVariables.getSquare().getChildren().add(cannonVariables.getEmpty());
        }

        return cannonVariables.getSquare();
    }

    /**
     * Determines which cannons are active during the current turn.
     * @param j the current row or column
     * @param cannonVariables object containing the necessary variables to determine and add the right image
     */
    private void addCannon(int j, CannonVariables cannonVariables) {
        if(game.getCurrentTurn()%2==0){
            for(int c: cannonVariables.getEvenCannons()){
                if(j==c){
                    addActiveInactiveCannon(true, cannonVariables);
                }
            }
            for(int c: cannonVariables.getOddCannons()){
                if(j==c){
                    addActiveInactiveCannon(false, cannonVariables);
                }
            }
        }
        else{
            for(int c: cannonVariables.getEvenCannons()){
                if(j==c){
                    addActiveInactiveCannon(false, cannonVariables);
                }
            }
            for(int c: cannonVariables.getOddCannons()){
                if(j==c){
                    addActiveInactiveCannon(true, cannonVariables);
                }
            }
        }
    }
    /**
     * Sets the right image for the {@code StackPane}.
     * @param active whether the cannon is active in the current round or not
     * @param cannonVariables object containing the variable needed to be set the right image
     */
    private void addActiveInactiveCannon(boolean active, CannonVariables cannonVariables){
        if(active){
            cannonVariables.getCannon().setImage(cannonVariables.getActiveCannon());
        }
        else{
            cannonVariables.getCannon().setImage(cannonVariables.getInactiveCannon());
        }
        cannonVariables.getCannon().setPreserveRatio(true);
        cannonVariables.getCannon().setSmooth(true);
        cannonActive(cannonVariables);
    }

    /**
     * Sets whether there is a cannon in the current row or column.
     * @param cannonVariables object containing the variable needed to be set true or false
     */
    private void cannonActive(CannonVariables cannonVariables){
        cannonVariables.setIsCannon(true);
    }

    private void initializeBoard(){
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createBoardSquare(i, j);
                board.add(square, j, i);
            }
        }
    }

    private StackPane createBoardSquare(int i, int j) {
        var square = new StackPane();
        square.getStyleClass().add("square");

        addPlayer(i, j, square);

        addBlockedSquareAndGoal(i, j, square);

        return square;
    }

    /**
     * Ads the player to the current square if the coordinates are the same
     * @param i current row of the board
     * @param j current column of the board
     * @param square the current square of the board
     */
    private void addPlayer(int i, int j, StackPane square){
        var player = new Circle(10);

        if (i==game.getPosition().row() && j==game.getPosition().col()){
            square.getChildren().add(player);
        }
    }

    /**
     * Colours the squares the player shouldn't step on and the goal.
     * @param i current row of the board
     * @param j current column of the board
     * @param square the current square of the board
     */
    private void addBlockedSquareAndGoal(int i, int j, StackPane square){
        if ((i==13 && j==13) || (i==14 && j==6)){
            addBlockedSquare(square);
        }
        if (i==14 && j==14){
            addGoal(square);
        }
    }
    private void addBlockedSquare(StackPane square){
        square.setStyle("-fx-background-color:BLACK");
    }
    private void addGoal(StackPane square){
        square.setStyle("-fx-background-color:#0750b3");
    }

    /**
     * Clears all cannons and the player's position.
     */
    private void clear() {
        clearCannonCols();
        clearCannonRows();
        clearBoard();
    }
    private void clearCannonCols(){
        for (var j = 0; j < cannonCols.getColumnCount(); j++) {
            cannonCols.getChildren().clear();
        }
    }
    private void clearCannonRows(){
        for (var i = 0; i < cannonRows.getRowCount(); i++) {
            cannonRows.getChildren().clear();
        }
    }

    private void clearBoard(){
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                board.getChildren().clear();
            }
        }
    }

    @FXML
    private void savePlayerName(){
        Logger.debug("Saving player name");
        playerName.setDisable(true);
    }

    private void registerKeyEventHandler() {
        Platform.runLater(() -> board.getScene().setOnKeyPressed(this::handleKeyPress));
    }

    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.UP) {
            upButton.fire();
        } else if (keyEvent.getCode() == KeyCode.RIGHT) {
            rightButton.fire();
        } else if (keyEvent.getCode() == KeyCode.DOWN) {
            downButton.fire();
        } else if (keyEvent.getCode() == KeyCode.LEFT) {
            leftButton.fire();
        }
    }

    @FXML
    private void handleUpButton() throws IOException {
        Logger.debug("UP pressed");
        play(Direction.UP);
            }
    @FXML
    private void handleRightButton() throws IOException {
        Logger.debug("RIGHT pressed");
        play(Direction.RIGHT);
    }
    @FXML
    private void handleDownButton() throws IOException {
        Logger.debug("DOWN pressed");
        play(Direction.DOWN);
    }
    @FXML
    private void handleLeftButton() throws IOException {
        Logger.debug("LEFT pressed");
        play(Direction.LEFT);
    }

    /**
     * Coordinates the rounds of the game, moving the player, and ending the game with the appropriate outcome.
     * @param direction which direction the player moves
     * @throws IOException {@code NextScene} throws the exception
     */
    private void play(Direction direction) throws IOException {
        numberOfSteps=numberOfSteps+1;
        Logger.info("Moving {}", direction);
        game.makeMove(direction);
        Logger.trace("New state after move: {}", game.toString());
        if (game.gameEnd())
        {
            if(!playerName.getText().equals("Player"))
            {
                manager.add(createGameResult(playerName.getText(), game.getCurrentTurn(), game.won()));
            }
            if(game.won()){
                Logger.debug("Player won");
                nextScene(true);
            }
            else{
                Logger.debug("Player lost");
                nextScene(false);
            }
        }
        clear();
        initialize();
    }

    /**
     * Opens and loads the winning or losing screen.
     * @param won whether the
     * @throws IOException if the fxml files are not found
     */
    private void nextScene(boolean won) throws IOException{
        Stage stage = (Stage) (board.getScene().getWindow());
        Parent root;
        if(won){
            Logger.debug("Moving to winning screen");
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/won.fxml")));
        }
        else{
            Logger.debug("Moving to losing screen");
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/lost.fxml")));
        }
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * {@return the object containing the player's data}
     * @param name name of the player
     * @param steps number of steps the player took
     * @param solved weather the player solved the puzzle or not
     */
    private static GameResult createGameResult(String name, int steps, boolean solved) {
        return GameResult.builder()
                .playerName(name)
                .solved(solved)
                .steps(steps)
                .build();
    }
}