import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class Bandit extends NPC {
    private int demand;

    public Bandit(Universe u, Region d, Player p) {
        super(u, d, p);
        name = "Bandit";
        demand = rand.nextInt(player.getCredits() * 2);
        message = "A bandit has appeared and is demanding " + demand + " credits!";
        credits = rand.nextInt(20000);
        System.out.println("bandit constructed"); //TODO debug
    }

    public void encounter() {
        System.out.println("starting bandit encounter"); //TODO debug
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(name + "encounter");
        HBox hbox = new HBox();
        Button btnPay = new Button("Pay");
        btnPay.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                pay();
            }
        });
        Button btnFight = new Button("Fight");
        btnFight.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                fight();
            }
        });
        Button btnFlee = new Button("Flee");
        btnFlee.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                flee();
            }
        });
        hbox.getChildren().addAll(btnPay, btnFight, btnFlee);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        Label lblMsg = new Label(message);
        vbox.getChildren().addAll(lblMsg, hbox);
        System.out.println("creating scene"); //TODO debug
        scene = new Scene(vbox);
        scene.getStylesheets().add("assets/retro-light.css");
        stage.setScene(scene);
        System.out.println("showing stage"); //TODO debug
        stage.showAndWait();
    }

    private void pay() {
        if (!player.removeCredits(demand)) { // if player didnt have enough credits
            if (player.getShip().getCargoCount() > 0) {
                player.getShip().emptyCargo();
                Notification.show("Encounter outcome", "You didn't have the credits to pay, so "
                        + "the bandit took your cargo and left you on your way");
            } else {
                dealDamage(15);
                Notification.show("Encounter outcome", "Without any credits or cargo to give the"
                        + " bandit, they fire on your ship and leave you on your way.");
            }
        } else {
            Notification.show("Encounter outcome", "You pay the bandit and continue on your way");
        }
        universe.travelTo(destination);
        stage.close();
    }

    private void fight() {
        if (rand.nextInt(100) + player.getSkills()[1] * 2 > 50) {
            Notification.show("Encounter outcome", "You successfully fend off the bandit, taking"
                    + " some of their credits and continuing on your way");
            player.addCredits(credits);
            universe.travelTo(destination);
        } else { //failure
            Notification.show("Encounter outcome", "You attempt to fight back but take damage "
                    + "and lose your credits before being kicked back to the system you came from");
            player.setCredits(0);
            dealDamage(15);
        }
        stage.close();
    }

    private void flee() {
        if (rand.nextInt(100) + player.getSkills()[0] * 2 > 50) {
            Notification.show("Encounter outcome", "You manage to flee successfully");
        } else { //failure to flee
            Notification.show("Encounter outcome", "You fail to flee; the bandit takes your "
                    + "credits, damages your ship, and sends you back whence you came");
            player.setCredits(0);
            dealDamage(15);
        }
        stage.close();
    }

    private void dealDamage(int d) {
        player.getShip().takeDamage(d);
    }
}