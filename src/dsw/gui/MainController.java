package dsw.gui;

import dsw.game.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MainController {
    private ToggleGroup difficultiesGroup = new ToggleGroup();
    private Minesweeper game = new MinesweeperGame();

    @FXML
    public GridPane gameGrid;

    @FXML
    public HBox difficultiesContainer;

    @FXML
    protected void initialize() {
        ObservableList<Node> difficultyPaneChildren = difficultiesContainer.getChildren();
        for (Difficulty difficulty : Difficulty.values()) {
            RadioButton option = createDifficultyOption(difficulty);
            difficultyPaneChildren.add(option);
        }
        difficultiesGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            Difficulty selectedDifficulty = (Difficulty) newValue.getUserData();
            resetState();
            startGame(selectedDifficulty);
        });
    }

    private RadioButton createDifficultyOption(Difficulty difficulty) {
        RadioButton option = new RadioButton(difficulty.toString());
        option.setToggleGroup(difficultiesGroup);
        option.setUserData(difficulty);
        return option;
    }

    private void startGame(Difficulty difficulty) {
        game.start(difficulty);
        //todo: started
    }

    private void resetState() {
        gameGrid.getChildren().clear();
        gameGrid.getColumnConstraints().clear();
        gameGrid.getRowConstraints().clear();
    }
}
