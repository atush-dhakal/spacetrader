import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.shape.Circle;

public class RButton extends Button {
    private Region region;
    public static final int RADIUS = 20;

    /**
     * The RButton constructor
     * @param reg the region
     */
    public RButton(Region reg) {
        region = reg;
        this.setContentDisplay(ContentDisplay.CENTER);
        this.setShape(new Circle(RADIUS));
        this.setStyle(
                "-fx-background-radius: 10em; " 
                        + "-fx-min-width: 10px; "
                        + "-fx-min-height: 10px; "
                        + "-fx-max-width: 10px; "
                        + "-fx-max-height: 10px;"
                        + "-fx-border-color: white;"
                        + "-fx-background-color: green;"
        );
    }

    /**
     * The current region of the player
     * @return region
     */
    public Region getRegion() {
        return region;
    }
}
