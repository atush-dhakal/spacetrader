import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Notification {

    public static void show(String title, String message) {
        //TODO popup window
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        Label lblMessage = new Label(message);
        Button btnOk = new Button("Okay");
        btnOk.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.close();
            }
        });
        vbox.getChildren().addAll(lblMessage, btnOk);
        Scene scene = new Scene(vbox);
        scene.getStylesheets().add("assets/retro-light.css");
        stage.setScene(scene);
        stage.showAndWait();
    }
}