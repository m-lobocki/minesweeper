package dsw.gui;

import dsw.game.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MainController {
    private ToggleGroup difficultiesGroup = new ToggleGroup();
    private Minesweeper game = new MinesweeperGame();

    //fixme: test data
    private MapObject[][] objects = new MapObject[][]{
            {new Bomb(), new Bomb(), new Field(1)},
            {new Field(2), new Field(3), new Field(2)},
            {new Field(0), new Field(1), new Bomb()}
    };

    @FXML
    public GridPane gameGrid;

    @FXML
    public HBox difficultiesContainer;

    @FXML
    protected void initialize() {
        displayGreeting();
        setUpDifficultiesPane();
    }

    private void displayGreeting() {
        Label greetingControl = new Label("Welcome to Minesweeper! Choose a difficulty above to start the game");
        greetingControl.getStyleClass().add("greeting");
        gameGrid.add(greetingControl, 0, 0);
    }

    private void setUpDifficultiesPane() {
        ObservableList<Node> difficultyPaneChildren = difficultiesContainer.getChildren();
        for (Difficulty difficulty : Difficulty.values()) {
            RadioButton option = createDifficultyOption(difficulty);
            difficultyPaneChildren.add(option);
        }
        difficultiesGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            Difficulty selectedDifficulty = (Difficulty) newValue.getUserData();
            resetGui();
            startGame(selectedDifficulty);
        });
    }

    private RadioButton createDifficultyOption(Difficulty difficulty) {
        RadioButton option = new RadioButton(difficulty.toString());
        option.setToggleGroup(difficultiesGroup);
        option.setUserData(difficulty);
        return option;
    }

    private void resetGui() {
        gameGrid.getChildren().clear();
    }

    private void startGame(Difficulty difficulty) {
        game.start(difficulty);
        for (int column = 0; column < objects.length; column++) {
            for (int row = 0; row < objects.length; row++) {
                Button mapObjectControl = new Button();
                mapObjectControl.setOnAction(this::mapObjectClicked);
                addControlToGameGrid(mapObjectControl, column, row);
            }
        }
    }

    private void mapObjectClicked(ActionEvent event) {
        Control sourceControl = (Control)event.getSource();
        //todo: click action
    }

    private void addControlToGameGrid(Control control, int column, int row) {
        GridPane.setVgrow(control, Priority.ALWAYS);
        GridPane.setHgrow(control, Priority.ALWAYS);
        control.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        control.getStyleClass().add("game-field");
        gameGrid.add(control, column, row);
    }
}
