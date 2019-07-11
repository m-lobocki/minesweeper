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
    private MapObject[][] objects;

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
            objects = game.start(selectedDifficulty);
            refreshGui();
        });
    }

    private RadioButton createDifficultyOption(Difficulty difficulty) {
        RadioButton option = new RadioButton(difficulty.toString());
        option.setToggleGroup(difficultiesGroup);
        option.setUserData(difficulty);
        return option;
    }

    private void mapObjectClicked(ActionEvent event) {
        Control sourceControl = (Control) event.getSource();
        MapObjectData objectData = (MapObjectData) sourceControl.getUserData();
        game.dig(objectData.getColumn(), objectData.getRow());
        if (game.checkLose()) {
            // todo: on lose
        }
        if (game.checkWin()) {
            // todo: on win
        }
        refreshGui();
    }

    private void refreshGui() {
        gameGrid.getChildren().clear();
        for (int column = 0; column < objects.length; column++) {
            for (int row = 0; row < objects.length; row++) {
                Button mapObjectControl = new Button();
                mapObjectControl.setOnAction(this::mapObjectClicked);
                mapObjectControl.setUserData(new MapObjectData(column, row));
                if (objects[column][row].getVisible()) {
                    mapObjectControl.getStyleClass().add("game-field--visible");
                    if (objects[column][row] instanceof Field) {
                        Field field = (Field) objects[column][row];
                        int surroundingBombs = field.getSurroundingBombs();
                        if (surroundingBombs != 0) {
                            mapObjectControl.setText(String.valueOf(surroundingBombs));
                        }
                    } else {
                        mapObjectControl.setText("*");
                        mapObjectControl.getStyleClass().add("game-field--bomb");
                    }
                }
                addControlToGameGrid(mapObjectControl, column, row);
            }
        }
    }

    private void addControlToGameGrid(Control control, int column, int row) {
        GridPane.setVgrow(control, Priority.ALWAYS);
        GridPane.setHgrow(control, Priority.ALWAYS);
        control.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        control.getStyleClass().add("game-field");
        gameGrid.add(control, column, row);
    }
}
