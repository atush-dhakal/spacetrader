import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class Police extends NPC {
    private int demandIndex; // index of sus goods

    public Police(Universe u, Region d, Player p) {
        super(u, d, p);
        name = "Police";
        demandIndex = rand.nextInt(10); // [Water,Furs,Food,Ore,Games,Fire,Med,Mach,Narc,Robot]
        while (player.getShip().getCargo()[demandIndex] < 1) {
            demandIndex = (demandIndex + 1) % 10;
        }
        String[] goods = new String[] {"water", "fur(s)", "food",
                "ore", "game(s)", "firearm(s)",
                "medicine", "machine(s)", "narcotics", "robot(s)"};
        message = "A police unit has appeared and is demanding that you hand over your "
                + goods[demandIndex] + " under suspicion of illegal obtainment!";
    }

    public void encounter() {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(name + "encounter");
        HBox hbox = new HBox();
        Button btnSubmit = new Button("Submit");
        btnSubmit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                submit();
            }
        });
        Button btnResist = new Button("Resist");
        btnResist.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                resist();
            }
        });
        Button btnFlee = new Button("Flee");
        btnFlee.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                flee();
            }
        });
        hbox.getChildren().addAll(btnSubmit, btnResist, btnFlee);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        Label lblMsg = new Label(message);
        vbox.getChildren().addAll(lblMsg, hbox);
        scene = new Scene(vbox);
        scene.getStylesheets().add("assets/retro-light.css");
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void submit() {
        Notification.show("Encounter outcome", "You offer up the requested goods and continue "
                + "on your way");
        confiscateGoods();
        universe.travelTo(destination);
        stage.close();
    }

    private void resist() {
        if (rand.nextInt(100) + player.getSkills()[1] * 2 > 50) {
            Notification.show("Encounter outcome", "You successfully resist the police and "
                    + "continue on your way");
            universe.travelTo(destination);
        } else { //failure
            Notification.show("Encounter outcome", "You attempt to resist the police, but they"
                    + " damage your ship and take your goods before sending back whence you came");
            confiscateGoods();
            dealDamage(20);
        }
        stage.close();
    }

    private void flee() {
        if (rand.nextInt(100) + player.getSkills()[0] * 2 > 50) {
            Notification.show("Encounter outcome", "You manage to flee successfully");
        } else { //failure to flee
            Notification.show("Encounter outcome", "You fail to flee, losing your goods, getting"
                    + " fined 1000 credits, taking damage, and getting sent back to the"
                    + " very region you departed from");
            confiscateGoods();
            dealDamage(20);
            if (!player.removeCredits(1000)) { //TODO soften this
                player.setCredits(0);
            }
        }
        stage.close();
    }

    private void confiscateGoods() {
        player.getShip().removeCargo(demandIndex, player.getShip().getCargo()[demandIndex]);
    }

    private void dealDamage(int d) {
        player.getShip().takeDamage(d);
    }
}