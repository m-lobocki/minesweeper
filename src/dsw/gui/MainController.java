package dsw.gui;

import dsw.game.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainController {
    private final int FIELD_SIDE_LENGTH = 40;
    private final int INITIAL_WINDOW_WIDTH = 400;
    private final String FLAG_CSS_CLASS = "game-field--flag";
    private ToggleGroup difficultiesGroup = new ToggleGroup();
    private Minesweeper game = new MinesweeperGame();
    private MapObject[][] objects;
    private boolean[][] flags;
    private Button restartButton;

    @FXML
    public BorderPane rootContainer;

    @FXML
    public TilePane gameGrid;

    @FXML
    public HBox menuContainer;

    @FXML
    protected void initialize() {
        displayGreeting();
        setUpMenuPane();
    }

    private void displayGreeting() {
        Label greetingControl = new Label("Welcome to Minesweeper! Choose a difficulty above to start the game");
        greetingControl.getStyleClass().add("greeting");
        gameGrid.getChildren().add(greetingControl);
        rootContainer.setMaxWidth(INITIAL_WINDOW_WIDTH);
    }

    private void setUpMenuPane() {
        ObservableList<Node> menuContainerChildren = menuContainer.getChildren();
        for (Difficulty difficulty : Difficulty.values()) {
            RadioButton option = createDifficultyOption(difficulty);
            menuContainerChildren.add(option);
        }
        addRestartButton();
        difficultiesGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            Difficulty selectedDifficulty = (Difficulty) newValue.getUserData();
            startGame(selectedDifficulty);
            restartButton.setDisable(false);
        });
    }

    private RadioButton createDifficultyOption(Difficulty difficulty) {
        RadioButton option = new RadioButton(difficulty.toString());
        option.setToggleGroup(difficultiesGroup);
        option.setUserData(difficulty);
        return option;
    }

    private void startGame(Difficulty difficulty) {
        objects = game.start(difficulty);
        flags = new boolean[game.getMapWidth()][game.getMapHeight()];
        refreshGui();
        centerScreen();
    }

    private void addRestartButton() {
        restartButton = new Button("Reset");
        restartButton.getStyleClass().add("restart-button");
        restartButton.setDisable(true);
        restartButton.setOnAction(event -> startGame((Difficulty) difficultiesGroup.getSelectedToggle().getUserData()));
        menuContainer.getChildren().add(restartButton);
    }

    private void refreshGui() {
        gameGrid.getChildren().clear();
        int mapWidth = game.getMapWidth();
        int mapHeight = game.getMapHeight();
        gameGrid.setPrefColumns(mapWidth);
        gameGrid.setPrefRows(mapHeight);
        for (int row = 0; row < mapHeight; row++) {
            for (int column = 0; column < mapWidth; column++) {
                Button mapObjectControl = new Button();
                mapObjectControl.setOnMouseClicked(this::mapObjectClicked);
                mapObjectControl.setUserData(new MapObjectData(column, row));
                mapObjectControl.setText("  ");
                MapObject currentMapObject = objects[column][row];
                if (flags[column][row]) {
                    mapObjectControl.getStyleClass().add(FLAG_CSS_CLASS);
                }
                if (currentMapObject.getVisible()) {
                    styleVisibleMapObject(mapObjectControl, currentMapObject);
                }
                addControlToGameGrid(mapObjectControl);
            }
        }
        rootContainer.setMaxWidth(Double.MAX_VALUE);
        gameGrid.getScene().getWindow().sizeToScene();
    }

    private void mapObjectClicked(MouseEvent event) {
        Control sender = (Control) event.getSource();
        MapObjectData objectData = (MapObjectData) sender.getUserData();
        if (event.getButton() == MouseButton.PRIMARY) {
            handleDig(objectData);
        }
        if (event.getButton() == MouseButton.SECONDARY) {
            toggleFlag(sender, objectData);
        }
        refreshGui();

        if (game.checkLose()) {
            showFinishedGameAlert("Oops", "You have lost!");
        }
        if (game.checkWin()) {
            showFinishedGameAlert("Congratulations", "You have won!");
        }
    }

    private void styleVisibleMapObject(Labeled mapObjectControl, MapObject mapObject) {
        mapObjectControl.getStyleClass().remove(FLAG_CSS_CLASS);
        mapObjectControl.getStyleClass().add("game-field--visible");
        mapObjectControl.setMouseTransparent(true);
        if (mapObject instanceof Field) {
            Field field = (Field) mapObject;
            int surroundingBombs = field.getSurroundingBombs();
            mapObjectControl.getStyleClass().add("game-field--" + surroundingBombs);
            if (surroundingBombs != 0) {
                mapObjectControl.setText(String.valueOf(surroundingBombs));
            }
        } else {
            mapObjectControl.getStyleClass().add("game-field--bomb");
        }
    }

    private void addControlToGameGrid(Control control) {
        GridPane.setVgrow(control, Priority.ALWAYS);
        GridPane.setHgrow(control, Priority.ALWAYS);
        control.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        control.getStyleClass().add("game-field");
        control.setPrefWidth(FIELD_SIDE_LENGTH);
        control.setPrefHeight(FIELD_SIDE_LENGTH);
        gameGrid.getChildren().add(control);
    }

    private void centerScreen() {
        Stage stage = (Stage) gameGrid.getScene().getWindow();
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        stage.setX((bounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((bounds.getHeight() - stage.getHeight()) / 2);
    }

    private void handleDig(MapObjectData objectData) {
        int column = objectData.getColumn();
        int row = objectData.getRow();
        game.dig(column, row);
        flags[column][row] = false;
    }

    private void toggleFlag(Control mapObjectControl, MapObjectData objectData) {
        int row = objectData.getRow();
        int column = objectData.getColumn();
        flags[column][row] = !flags[column][row];
        if (flags[column][row]) {
            mapObjectControl.getStyleClass().add(FLAG_CSS_CLASS);
        } else {
            mapObjectControl.getStyleClass().remove(FLAG_CSS_CLASS);
        }
    }

    private void showFinishedGameAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.NONE, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
