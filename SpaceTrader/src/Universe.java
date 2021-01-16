import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.Random;

public class Universe {
    private ArrayList<Region> regions;
    private int playerRegion; // index of region that player is at
    //    public static final int width = 480, height = 270;
    private final int width = 540;
    private final int height = 540;
    private final int padding = 20;
    private Region selectedRegion;
    private Region currentRegion;
    private ArrayList<Region> nearbyRegions;
    private Circle currCircle;
    private Circle selectedCircle;
    private Game game;
    private Player player;
    private Random rand;

    /**
     * The univerese where all regions will be displayed
     * @param numRegions the number of regions
     * @param g the game
     * @param p the player
     */
    public Universe(int numRegions, Game g, Player p) {
        game = g; //TODO this is a dumb way to move data. code smells.
        player = p; //TODO code smells
        rand = new Random();
        currCircle = new Circle(200); //TODO make this dependent on ship range
        currCircle.setStroke(Color.GRAY);
        currCircle.setFill(Color.TRANSPARENT);
        currCircle.setMouseTransparent(true);
        selectedCircle = new Circle(11);
        selectedCircle.setStroke(Color.WHITE);
        selectedCircle.setFill(Color.TRANSPARENT);
        selectedCircle.setMouseTransparent(true);

        regions = new ArrayList<Region>();
        nearbyRegions = new ArrayList<Region>();
        // place regions smartly but randomly (no collisions, no unreachable regions)
        int h = 0;
        int k = 0;
        int i = 0;
        int cellSize = (int) Math.sqrt(numRegions);
        for (int x = 0; x < cellSize; x++) {
            for (int y = 0; y < cellSize; y++) {
                h = (int) (((double) x + Math.random()) * cellSize / numRegions * width);
                k = (int) (((double) y + Math.random()) * cellSize / numRegions * height);
                h = Math.min(Math.max(h, padding), width - padding); //TODO
                k = Math.min(Math.max(k, padding), height - padding); //TODO
                regions.add(new Region(h, k, i));
                i++;
            }
        }
        selectedRegion = regions.get(numRegions / 2);
    }

    /**
     * Arraylist of regions
     * @return region
     */
    public ArrayList<Region> getRegions() {
        return regions;
    }

    /**
     * The distance between two regions
     * @param r1 the region 1 co-ordinate
     * @param r2 the region 2 co-ordinate
     * @return the distance
     */

    public static double dist(Region r1, Region r2) {
        System.out.println("starting dist"); //TODO debug
        System.out.println("r1 name: " + r1.getName()); //TODO debug
        System.out.println("r2 name: " + r2.getName()); //TODO debug
        return Math.sqrt(Math.pow(r1.getX() - r2.getX(), 2) + Math.pow(r1.getY() - r2.getY(), 2));
    }

    /**
     * Pane to display region
     * @param pane the pane
     * @return pane with regions
     */
    public Pane loadMap(Pane pane) {
        pane.setStyle("-fx-background-color: black;");
        for (Region r : regions) {
            RButton button = new RButton(r);
            button.setCenterShape(true);
            button.setOnAction(e -> {
                setSelectedRegion(button.getRegion());
            });
            button.setLayoutX(r.getX() - 5); //TODO
            button.setLayoutY(r.getY() - 5); //TODO
            pane.getChildren().add(button);
        }
        pane.getChildren().addAll(currCircle, selectedCircle);

        travelTo(regions.get((int) (Math.random() * regions.size())));
        setSelectedRegion(nearbyRegions.get(0));
        return pane;
    }

    /**
     * Player will travel to the region of their choice
     */
    public void travelToSelected() {
        System.out.println("attempting to travelToSelected"); //TODO debug
        if (nearbyRegions.contains(selectedRegion)) {
            if (!player.getShip().reduceFuel(Math.max(1, (int) dist(currentRegion, selectedRegion)
                    / 10 - player.getSkills()[0]))) { // check fuel
                Notification.show("Low Fuel Warning", "You don't have enough fuel to make it to"
                        + " this destination!");
                return;
            }

            if (player.getCredits() > 0
                    && rand.nextInt(7 - game.getDifficulty()) == 0) {
                System.out.println("bandit rolled"); //TODO debug
                new Bandit(this, selectedRegion, player).encounter();
            } else if (player.getShip().getCargoCount() > 0
                    && rand.nextInt(7 - game.getDifficulty()) == 0) {
                System.out.println("police rolled"); //TODO debug
                new Police(this, selectedRegion, player).encounter();
            } else if (player.getShip().getCargoCount() < player.getShip().getCargoCapacity()
                    && rand.nextInt(1 + game.getDifficulty()) == 0) {
                System.out.println("trader rolled"); //TODO debug
                new Trader(this, selectedRegion, player).encounter();
            } else {
                travelTo(selectedRegion);
            }
            game.updateStats();
            game.updateMarket();
        }
    }

    /**
     * Enables players to travel to a region
     * @param reg the region
     */
    public void travelTo(Region reg) {
        System.out.println("trying to travelTo  " + reg.getName()); //TODO debug
        nearbyRegions.clear();
        currentRegion = reg;
        System.out.println("updating nearby from regions"); //TODO debug
        for (Region r : regions) {
            System.out.println("checking dist of region " + r.getName()); //TODO debug
            if (r != currentRegion && Universe.dist(r, currentRegion) < 200) {
                nearbyRegions.add(r);
            }
        }
        currCircle.setCenterX(currentRegion.getX());
        currCircle.setCenterY(currentRegion.getY());
        reg.setVisited(true);
        game.updateCurrent(currentRegion);
        setSelectedRegion(nearbyRegions.get((int) (Math.random() * nearbyRegions.size())));
    }

    /**
     * Get previous region of the player
     */
    public void selectPrev() {
        if (nearbyRegions.contains(selectedRegion)) {
            int currInd = nearbyRegions.indexOf(selectedRegion);
            int size = nearbyRegions.size();
            setSelectedRegion(nearbyRegions.get((currInd - 1 + size) % size));
        } else {
            setSelectedRegion(nearbyRegions.get(0));
        }
    }

    /**
     * Get next region of the player
     */
    public void selectNext() {
        if (nearbyRegions.contains(selectedRegion)) {
            int currInd = nearbyRegions.indexOf(selectedRegion);
            setSelectedRegion(nearbyRegions.get((currInd + 1) % nearbyRegions.size()));
        } else {
            setSelectedRegion(nearbyRegions.get(0));
        }
    }

    /**
     * Region of the choice of the player
     * @param sr the region
     */
    public void setSelectedRegion(Region sr) {
        selectedRegion = sr;
        selectedCircle.setCenterX(selectedRegion.getX());
        selectedCircle.setCenterY(selectedRegion.getY());
        game.updateTarget(selectedRegion);
    }

    /**
     * Get current region
     * @return current region
     */
    public Region getCurrentRegion() {
        return currentRegion;
    }

    /**
     * Get region selected by the player
     * @return the region selected by the player
     */
    public Region getSelectedRegion() {
        return selectedRegion;
    }

}