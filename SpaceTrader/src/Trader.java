import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class Trader extends NPC {
    private Button btnNegotiate;
    private Label lblMsg;
    private int price;
    private int goodIndex;
    private int goodQuantity;
    private final String[] goods = new String[] {"water", "fur(s)", "food", "ore", "game(s)",
            "firearm(s)", "medicine(s)", "machine(s)",
            "narcotic(s)", "robot(s)"};
    private final int[] pricesLow = new int[] {30, 250, 105, 390, 180, 725, 510, 690, 2625, 3950};
    private final int[] pricesHigh = new int[] {54, 320, 135, 490, 240, 1175, 630, 810, 3500, 4400};

    public Trader(Universe u, Region d, Player p) {
        super(u, d, p);
        name = "Trader";
        lblMsg = new Label();
        goodIndex = rand.nextInt(10);
        goodQuantity = rand.nextInt(player.getShip().getCargoCapacity()
                - player.getShip().getCargoCount()) + 1;
        price = (rand.nextInt(pricesHigh[goodIndex] - pricesLow[goodIndex] + 1)
                + pricesLow[goodIndex]) * goodQuantity;
        updateMessage();
    }

    public void encounter() {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(name + "encounter");
        HBox hbox = new HBox();
        Button btnBuy = new Button("Buy");
        btnBuy.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                buy();
            }
        });
        btnNegotiate = new Button("Negotiate");
        btnNegotiate.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                negotiate();
            }
        });
        Button btnIgnore = new Button("Ignore");
        btnIgnore.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ignore();
            }
        });
        Button btnRob = new Button("Rob");
        btnRob.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                rob();
            }
        });
        hbox.getChildren().addAll(btnBuy, btnNegotiate, btnIgnore, btnRob);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(lblMsg, hbox);
        scene = new Scene(vbox);
        scene.getStylesheets().add("assets/retro-light.css");
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void buy() {
        if (player.removeCredits(price)) {
            Notification.show("Encounter outcome", "You purchase the trader's goods and continue"
                    + " on your way");
            giveGoods(true);
            universe.travelTo(destination);
            stage.close();
        } else {
            Notification.show("Encounter outcome", "You can't afford the current price");
        }
    }

    private void ignore() {
        Notification.show("Encounter outcome", "You dismiss the trader and go on your way");
        universe.travelTo(destination);
        stage.close();
    }

    private void rob() {
        if (rand.nextInt(100) + player.getSkills()[1] * 2 > 50) {
            Notification.show("Encounter outcome", "You rob the trader of some of his goods and"
                    + " continue on your way");
            giveGoods(false);
        } else { //failure
            Notification.show("Encounter outcome", "You attempt to rob the trader, but fail, "
                    + "taking damage in the process");
            dealDamage(15);
        }
        universe.travelTo(destination);
        stage.close();
    }

    private void negotiate() {
        if (rand.nextInt(100) + player.getSkills()[2] * 2 > 50) {
            Notification.show("Negotiation outcome", "You successfully negotiate for a better"
                    + " deal");
            price = (int) (price * 0.8); //TODO soften this up
        } else { //failure negotiate
            Notification.show("Negotiation outcome", "Your haggling upsets the trader and they"
                    + " raise the price");
            price = (int) (price * 1.2);
        }
        updateMessage();
        btnNegotiate.setDisable(true);
    }

    private void dealDamage(int d) {
        player.getShip().takeDamage(d);
    }

    private void updateMessage() {
        message = "A trader is offering " + goodQuantity + " " + goods[goodIndex]
                + " for " + price + " credits";
        lblMsg.setText(message);
    }

    private void giveGoods(boolean all) {
        player.getShip().addCargo(goodIndex, all ? goodQuantity : goodQuantity / 2);
    }
}