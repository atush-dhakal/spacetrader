import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Game extends Application {
    private Stage stage;
    private Button btnPrev;
    private Button btnNext;
    private Button btnWarp;
    private Button btnRefuel;
    private BorderPane bpMain;
    private Pane pMap;
    private VBox vbLeft;
    private VBox vbRight;
    private HBox hbTop;
    private HBox hbButtons;
    private Label lblCurrSys;
    private Label lblCurrTech;
    private Label lblCurrDesc;
    private Label lblCurrCoords;
    private Label lblTargetSys;
    private Label lblTargetDesc;
    private Label lblTargetCoords;
    private Label lblTargetTech;
    private Label lblTargetDist;
    private Region currentRegion;
    private Region targetRegion;
    private GridPane market;
    private int[] currSelling;
    private int[] currBuying;
    private static final String[] GOODS = new String[] {
            "Water",
            "Furs",
            "Food",
            "Ore",
            "Games",
            "Firearms",
            "Medicine",
            "Machines",
            "Narcotics",
            "Robots"
    };
    private Label lblCredits;
    private Label lblHealth;
    private Label lblFuel;

    private Player player;
    private Universe universe;
    private int difficulty;

    /**
     * Launches the game
     * @param window the window of the game
     * @param diffLvl difficulty level
     * @param skillsIn the skill points of the player
     * @param credsIn the credits for the game
     */
    public Game(Stage window, int diffLvl, int[] skillsIn, int credsIn) {
        player = new Player(skillsIn, credsIn);
        player.setShip(new Gnat());
        difficulty = diffLvl;
        universe = new Universe(16, this, player); //TODO this is temporarily hardcoded to 16
        start(window);
    }

    @Override
    public void start(Stage stage) {
        Font.loadFont(getClass().getResourceAsStream("assets/Pokemon Classic.ttf"), 14);

        HBox h = new HBox();

        btnRefuel = new Button();
        btnRefuel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (player.removeCredits(
                        (player.getShip().getFuelCapacity() - player.getShip().getFuel())
                                * currentRegion.getFuelCost())) { // couldnt afford full
                    player.getShip().refuel();
                } else if (player.removeCredits(1)) {
                    player.getShip().addFuel(1);
                }
                updateStats();
            }
        });

        lblCurrSys = new Label("Current System: init");
        lblCurrDesc = new Label();
        lblCurrTech = new Label("Tech level: init");
        lblCurrCoords = new Label("Coordinates: init");
        lblTargetSys = new Label("Target System: init");
        lblTargetDesc = new Label();
        lblTargetCoords = new Label("Coordinates: init");
        lblTargetTech = new Label("Tech Level: init");
        lblTargetDist = new Label("Distance: init");
        lblCredits = new Label("Credits: " + player.getCredits());
        lblHealth = new Label("Hull Integrity: " + player.getShip().getHealth());
        lblFuel = new Label("Fuel: " + player.getShip().getFuel());

        //TODO M5 initialize market gridpane here.
        // It should have 5 columns (good, player quantity, sell btn, region quantity, buy btn).
        // It should have 11 rows (One row for column headers and then one row for each good)
        market = new GridPane();
        market.setHgap(8);
        market.setVgap(4);
        //market.setGridLinesVisible(true); causes error with introducing labels
        market.addRow(0,
                new Label("Goods"),
                new Label("Your Qty"),
                new Label("Sell (cr)"),
                new Label("Region Qty"),
                new Label("Buy (cr)"));
        for (int i = 0; i < 10; i++) {
            market.addRow(i + 1,
                    new Label(GOODS[i]),
                    new Label(),
                    makeTransactionButton(i, "Sell"),
                    new Label(),
                    makeTransactionButton(i, "Buy"));
        }

        VBox v = new VBox();
        v.setMinWidth(370);
        v.setMaxWidth(370);
        v.setPadding(new Insets(5));
        v.getChildren().addAll(lblCurrSys,
                lblCurrDesc,
                lblCurrCoords,
                lblCurrTech,
                market,
                lblCredits,
                lblHealth,
                lblFuel,
                btnRefuel);
        Pane map = new Pane();
        //universe.loadMap(map);
        Rectangle clip = new Rectangle(540, 540);
        clip.setLayoutX(map.getLayoutX());
        clip.setLayoutY(map.getLayoutY());
        map.setClip(clip);
        map.setMinSize(540, 540);
        map.setMaxSize(540, 540);
        //TODO M5 put map in a VBox and add an HBox below it to hold upgrades/ships/weapons info.
        // The upgrades ships and weapons info can be a GridPane because
        // it will have one label and one button each.
        VBox v2 = new VBox();
        v2.setMinWidth(370);
        v2.setMaxWidth(370);
        v2.setPadding(new Insets(5));

        hbButtons = new HBox();
        btnPrev = new Button("<");
        btnPrev.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                universe.selectPrev();
            }
        });
        btnWarp = new Button("Warp");
        btnWarp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                universe.travelToSelected();
            }
        });
        btnNext = new Button(">");
        btnNext.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                universe.selectNext();
            }
        });
        hbButtons.getChildren().addAll(btnPrev, btnWarp, btnNext);

        v2.getChildren().addAll(lblTargetSys,
                lblTargetDesc,
                lblTargetCoords,
                lblTargetTech,
                lblTargetDist,
                hbButtons); //TODO M5 add a gridpane at the bottom to hold market info

        h.getChildren().addAll(v, map, v2);

        currSelling = new int[10];
        currBuying = new int[10];
        universe.loadMap(map);

        Scene mainUI = new Scene(h, 1280, 720);
        mainUI.getStylesheets().add("assets/main.css");
        stage.sizeToScene();
        stage.setScene(mainUI);
    }

    /**
     * Update the target region
     * @param r region in the universe
     */
    public void updateTarget(Region r) {
        targetRegion = r;
        lblTargetSys.setText("Target system: "
                + (targetRegion.getVisited() ? targetRegion.getName() : "UNKNOWN"));
        lblTargetDesc.setText("Description: "
                + (targetRegion.getVisited() ? targetRegion.getDesc() : "UNKNOWN"));
        lblTargetCoords.setText("Coordinates: ("
                + targetRegion.getX() + ", " + targetRegion.getY() + ")");
        lblTargetTech.setText("Tech Level: "
                +  (targetRegion.getVisited() ? targetRegion.getTechLvl().getName() : "UNKNOWN"));
        lblTargetDist.setText("Distance: "
                + (Math.round(Universe.dist(currentRegion, targetRegion))));
    }

    /**
     * Updates the region
     * @param r the current region
     */
    public void updateCurrent(Region r) {
        currentRegion = r;
        lblCurrSys.setText("Current System: "
                + currentRegion.getName());
        lblCurrDesc.setText("Description: "
                + currentRegion.getDesc());
        lblCurrCoords.setText("Coordinates: ("
                + currentRegion.getX() + ", " + currentRegion.getY() + ")");
        lblCurrTech.setText("Tech Level: "
                + currentRegion.getTechLvl().getName());

        updateMarket();
    }

    private Button makeTransactionButton(int goodID, String str) {
        Button btn = new Button(str);
        btn.setOnAction(e -> {
            if (str.toLowerCase().contains("sell")) {
                if (player.getShip().removeCargo(goodID, 1)) {
                    player.addCredits(currBuying[goodID]);
                    updateMarket();
                    updateStats();
                }
            } else {
                if (player.getShip().addCargo(goodID, 1)
                        && player.getCredits() >= currSelling[goodID]
                        && currentRegion.getGoods()[goodID] > 0) {
                    player.removeCredits(currSelling[goodID]);
                    currentRegion.decrementGood(goodID);
                    updateMarket();
                    updateStats();
                }
            }
        });
        return btn;
    }

    public void updateMarket() {
        for (int i = 0; i < 10; i++) {
            currSelling[i] = (int) (currentRegion.getSelling()[i]
                    * (1 + player.getSkills()[2] / 100.0));
            currBuying[i] = (int) (currentRegion.getBuying()[i]
                    * (1 - player.getSkills()[2] / 100.0));
        }

        for (int r = 1; r <= 10; r++) {
            ((Label) (market.getChildren().get(r * 5 + 1))).setText(
                    String.valueOf(player.getShip().getCargo()[r - 1]));
            ((Button) (market.getChildren().get(r * 5 + 2))).setText(
                    String.valueOf(currBuying[r - 1]));
            ((Label) (market.getChildren().get(r * 5 + 3))).setText(
                    String.valueOf(currentRegion.getGoods()[r - 1]));
            ((Button) (market.getChildren().get(r * 5 + 4))).setText(
                    String.valueOf(currSelling[r - 1]));
        }

        btnRefuel.setText("Refuel for " + currentRegion.getFuelCost() + " credits per unit");
    }

    public void updateStats() {
        lblCredits.setText("Credits: " + player.getCredits());
        lblFuel.setText("Fuel: " + player.getShip().getFuel());
        lblHealth.setText("Hull Integrity: " + player.getShip().getHealth());
    }

    public int getDifficulty() {
        return difficulty;
    }
}