/**
 * CS2340 Space Trader Game
 * M3
 * Team 64 - long
 * Version: 02/02/2020
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SpaceTrader extends Application {
    private Stage window;
    private Label validateSkillPoint;
    private int skillPoints;
    private int difficulty;
    private int[] skills;
    private int credits;
    private Label[] skillLabels;
    private Scene welcomePage;
    private Scene initialConfig;
    private Scene characterSheet;
    private Label welcome;
    private Button startGame;
    private Button endGame;
    private Label footer;
    private VBox box;
    private AudioController audioController;

    /**
     * Main method to launch the programme
     * @param args the argument
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        audioController = new AudioController();
        window = primaryStage;
        window.setResizable(false);
        window.setWidth(960);
        window.setHeight(540);
        Font.loadFont(getClass().getResourceAsStream("assets/Pokemon Classic.ttf"), 14);
        loadWelcomeScene();
        window.setTitle("Space Trader");
        window.show();

        //will be triggered when user presses Start Game Button
        startGame.setOnAction(actionEvent -> {
            loadConfigScene();
        });
    }

    /**
     * Increase the button based on slot and amount
     * @param slot the slot value
     * @param amount the amount to increase with
     * @return btn the button
     */
    private Button makeIncrementer(int slot, int amount) {
        Button btn = new Button(amount > 0 ? "+" : "-");
        btn.setOnAction(e -> {
            if ((amount < 0 && skills[slot] > 1)
                    || (amount > 0 && skillPoints > 0 && skills[slot] < 10)) {
                skills[slot] += amount;
                skillPoints -= amount;
                skillLabels[slot].setText(String.valueOf(skills[slot]));
                validatePoints();
            }
        });
        return btn;
    }

    /**
     * Validate's skill points based on difficulty level and other factors
     */
    private void validatePoints() {
        if (skillPoints < 0) {
            validateSkillPoint.setText("Too many points!");

        } else {
            validateSkillPoint.setText("");
        }
    }

    /**
     * loads welcome screen
     * allows users to start or exit the game
     */
    private void loadWelcomeScene() {
        welcome = new Label("Space Trader");
        welcome.setStyle("-fx-text-fill: white; -fx-font: 26px \"Pokemon Classic\";");

        ImageView flea = new ImageView(new Image("assets/fleaAnim4X.gif"));

        startGame = new Button("Start Game");

        endGame = new Button("Exit Game");
        endGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Platform.exit();
                System.exit(0);
            }
        });

        footer = new Label("Designed by long Inc. Copyright 2020. ");
        footer.setStyle("-fx-font: 12px \"Pokemon Classic\";");
        footer.setAlignment(Pos.CENTER);

        //add welcome message, start game button and end game button to the VBox
        box = new VBox();
        box.getChildren().addAll(welcome, flea, startGame, endGame);
        box.setSpacing(20);
        box.setAlignment(Pos.CENTER);

        //add VBox to the BorderPane
        BorderPane pane = new BorderPane();
        pane.setCenter(box);
        pane.setBottom(footer);
        pane.setBackground(new Background(
                new BackgroundImage(
                        new Image("assets/welcomeAnim4X.gif"),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(BackgroundSize.AUTO,
                                BackgroundSize.AUTO,
                                false,
                                false,
                                true,
                                true))));

        welcomePage = new Scene(pane);
        welcomePage.getStylesheets().add("assets/retro-dark.css");

        window.setScene(welcomePage);
    }

    /**
     * Loads the configuration screen
     * Allows users to enter name, choose difficulty level and skill points
     */
    private void loadConfigScene() {
        Label header = new Label("Game Configuration");
        header.setStyle("-fx-text-fill: black; -fx-font: 26px \"Pokemon Classic\";");
        header.setAlignment(Pos.CENTER);

        TextField name = new TextField();
        name.setPromptText("Enter Your Name");

        ComboBox<String> difficultyLevel = new ComboBox<>();
        difficultyLevel.getItems().addAll("Beginner (24 SP, 2000 c)",
                "Easy (22 SP, 1500 c)",
                "Medium (20 SP, 1000 c)",
                "Hard (18 SP, 500 c)",
                "Impossible (10 SP, 100 c)");
        difficultyLevel.setPromptText("Difficulty (Skill Points, Credits)");
        difficultyLevel.setOnAction(e -> {
            switch (difficultyLevel.getValue().split(" ")[0]) {
                case "Beginner":
                    difficulty = 1;
                    skillPoints = 24;
                    credits = 2000;
                    break;
                case "Easy":
                    difficulty = 2;
                    skillPoints = 22;
                    credits = 1500;
                    break;
                case "Medium":
                    difficulty = 3;
                    skillPoints = 20;
                    credits = 1000;
                    break;
                case "Hard":
                    difficulty = 4;
                    skillPoints = 18;
                    credits = 500;
                    break;
                case "Impossible":
                    difficulty = 5;
                    skillPoints = 10;
                    credits = 100;
                    break;
                default:
                    difficulty = 0;
                    skillPoints = 0;
                    credits = 0;
            }
            for (int p : skills) {
                skillPoints -= p;
            }
            validatePoints();
        });

        validateSkillPoint = new Label();
        validateSkillPoint.setAlignment(Pos.CENTER);
        validateSkillPoint.setStyle("-fx-text-fill: red;");

        Button backToWelcomePage = new Button("Back");
        backToWelcomePage.setMaxWidth(Double.MAX_VALUE);

        Button proceed = new Button("Proceed");
        proceed.setMaxWidth(Double.MAX_VALUE);

        BooleanBinding nameIsValid = new BooleanBinding() {
            {
                super.bind(name.textProperty());
                super.bind(difficultyLevel.valueProperty());
                super.bind(validateSkillPoint.textProperty());
            }
            @Override
            protected boolean computeValue() {
                return (name.getText().isEmpty()
                        || difficultyLevel.getValue() == null
                        || !validateSkillPoint.getText().isEmpty());
            }
        };
        proceed.disableProperty().bind(nameIsValid);

        VBox form = new VBox();
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(20, 20, 20, 20));
        form.setSpacing(8);

        GridPane points = new GridPane();
        points.setVgap(4);
        points.setHgap(8);

        skills = new int[]{1, 1, 1, 1}; // [pilot, fighter, trader, engineer]
        skillLabels = new Label[4];
        for (int i = 0; i < 4; i++) {
            skillLabels[i] = new Label("1");
            points.add(makeIncrementer(i, -1), 1, i);
            points.add(makeIncrementer(i, 1), 3, i);
            points.add(skillLabels[i], 2, i);
        }
        points.add(new Label("Pilot"), 0, 0);
        points.add(new Label("Fighter"), 0, 1);
        points.add(new Label("Trader"), 0, 2);
        points.add(new Label("Engineer"), 0, 3);

        HBox bottomRow = new HBox();
        bottomRow.setSpacing(8);
        bottomRow.setAlignment(Pos.CENTER);
        bottomRow.getChildren().addAll(backToWelcomePage, validateSkillPoint, proceed);
        for (Node n : bottomRow.getChildren()) {
            HBox.setHgrow(n, Priority.ALWAYS);
        }

        form.getChildren().addAll(header, name, difficultyLevel, points, bottomRow);

        for (Node n : form.getChildren()) {
            GridPane.setFillWidth(n, true);
        }

        //BorderPane pane01 = new BorderPane();
        //pane01.setCenter(form);
        Pane pane01 = new Pane(form); //TODO resize form and center it somehow
        initialConfig = new Scene(pane01);
        initialConfig.getStylesheets().add("assets/retro-light.css");

        window.setScene(initialConfig);

        //takes user back to welcome page
        backToWelcomePage.setOnAction(actionEvent1 -> {
            window.setScene(welcomePage);
        });

        //One user proceeds, it will display all character name, difficulty level and points
        proceed.setOnAction(actionEvent1 -> {
            loadTempScene(name.getText(), difficultyLevel.getValue());
        });
    }

    /**
     * Load the character screen
     * @param name the name of the users
     * @param difficultyLevel the difficulty level of user's choice
     */
    private void loadTempScene(String name, String difficultyLevel) {
        Button backToInitialConfig = new Button("Back");
        backToInitialConfig.setOnAction(actionEvent2 -> {
            window.setScene(initialConfig);
        });

        //Exit's the game
        Button beginGame = new Button("Let's Go!");
        beginGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                beginGame();
            }
        });

        //Organize all labels in a grid pane
        GridPane displayUserConfig = new GridPane();
        displayUserConfig.setAlignment(Pos.CENTER);
        displayUserConfig.setPadding(new Insets(20, 20, 20, 20));
        displayUserConfig.setVgap(10);
        displayUserConfig.setHgap(30);

        for (int i = 0; i < 4; i++) {
            displayUserConfig.add(new Label(String.valueOf(skills[i])), 1, i + 2);
        }
        displayUserConfig.add(new Label("Your name:"), 0, 0);
        displayUserConfig.add(new Label(name), 1, 0);
        displayUserConfig.add(new Label("Difficulty Level:"), 0, 1);
        displayUserConfig.add(new Label(difficultyLevel), 1, 1);
        displayUserConfig.add(new Label("Pilot Skill Points:"), 0, 2);
        displayUserConfig.add(new Label("Fighter Skill Points:"), 0, 3);
        displayUserConfig.add(new Label("Trader Skill Points:"), 0, 4);
        displayUserConfig.add(new Label("Engineer Skill Points:"), 0, 5);
        displayUserConfig.add(new Label("Credits:"), 0, 6);
        displayUserConfig.add(new Label(String.valueOf(credits)), 1, 6);
        displayUserConfig.add(backToInitialConfig, 0, 7);
        displayUserConfig.add(beginGame, 1, 7);

        BorderPane pane11 = new BorderPane(); //TODO why isn't this centering it
        pane11.setCenter(displayUserConfig);


        characterSheet = new Scene(pane11);
        characterSheet.getStylesheets().add("assets/retro-light.css");

        window.setScene(characterSheet);


    }

    /**
     * Begins the game
     */
    public void beginGame() {
        Game game = new Game(window, difficulty, skills, credits);
    }
}