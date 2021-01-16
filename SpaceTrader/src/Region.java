import java.util.Random;

public class Region {

    private static final String[] NAMES = new String[] {
            "gazorpazorp", "beezow", "doodoo", "zoppity-bop", "bopbop", "krypton",
            "skiles", "bazinga", "klaus", "clough", "asdf", "cvbuuobd", "qwertyuiop", "namesarehard",
            "ahhhhhhh", "system 32"};

    private static final String[] DESCS = new String[]{
            "Inhabited by bipedal Gazorpians. This system is barren of high level intelligence.",
            "The first of the 'Beezow-Doodoo-Zoppity-Bop-BopBop' collection of regions"
                    + " discovered in the mid-2000s"
                    + " (Human AD) and named after a rampant criminal.)",
            "The second of the 'Beezow-Doodoo-Zoppity-Bop-BopBop' collection of regions"
                    + " discovered in the mid-2000s"
                    + " (Human AD) and named after a rampant criminal.",
            "The third of the 'Beezow-Doodoo-Zoppity-Bop-BopBop' collection of regions"
                    + " discovered in the mid-2000s"
                    + " (Human AD) and named after a rampant criminal.",
            "The fourth of the 'Beezow-Doodoo-Zoppity-Bop-BopBop' collection of regions"
                    + " discovered in the mid-2000s"
                    +  "(Human AD) and named after a rampant criminal.",
            "Home of the Kryptonians, there have been many Earth movies fashioned around"
                    + " its likeness.",
            "Though eerily vacant after hours, every planet in this system sports a"
                    + " bustling variety of life during"
                    + " its daytime",
            "Named after a key joke in a pathetic human sitcom loosely related to the"
                    + " beginnings "
                    + "of this very universe",
            "Full of high-functioning introverts, this region has very little room"
                    + " for maneuvering and appropriately"
                    +  " heavy space traffic.",
            "Abbreviated from Clough Undergraduate Learning Commons, billions of aspiring"
                    + " traders pass through this"
                    +  " system each decade.",
            "When charting this system, the record-keeper suffered a stroke and failed to"
                    + " properly type its name."
                    + " Now, it is forever lost to the grasp of forgotten histories.",
            "The record-keeper in charge of this system's files suffered his second stroke"
                    + " in the course of two"
                    + " months, rendering yet another system effectively unnamed and"
                    + " eternally mysterious to space"
                    + " cartographers.",
            "With too many existing systems, the pioneer who ventured to this system first"
                    + " named it after the first"
                    + " thing she saw: her onboard control interface.",
            "Descriptions are hard, too.",
            "A distress beacon containing a single dying scream emanated from this system"
                    + " eons ago, hence the name."
                    + " It is unknown to this day what made the signal or why.",
            "The only common knowledge regarding this system is that it must never be"
                    + " trifled with, or a cataclysmic"
                    + " tragedy will strike the universe as we know it.",
    };

    // [Water,Furs,Food,Ore,Games,Fire,Med,Mach,Narc,Robot]
    private static final int[] PRICES_LOW = new int[] {
            30, 250, 105, 390, 180, 725, 510, 690, 2625, 3950};
    private static final int[] PRICES_HIGH = new int[] {
            54, 320, 135, 490, 240, 1175, 630, 810, 3500, 4400};

    private String name;
    private String desc;
    private int x;
    private int y;
    //private Govt govt; TODO
    private TechLvl techLvl;
    private RegionSize rSize;
    private boolean visited;
    private int fuelCost;
    private int[] goods;
    private int[] selling;
    private int[] buying;

    private static Random rand = new Random();

    /**
     * Region constructor
     * @param x the x co-ordinate
     * @param y the y co-ordinate
     * @param seed the seep in the region
     */
    public Region(int x, int y, int seed) {
        name = NAMES[seed];
        desc = DESCS[seed];
        this.x = x;
        this.y = y;
        visited = false;
        rSize = RegionSize.randomSize();
        techLvl = TechLvl.randomLvl();

        fuelCost = rand.nextInt(3) + 4; // avg of 5 per unit
        goods = new int[10];
        selling = new int[10];
        buying = new int[10];
        for (int i = 0; i < PRICES_LOW.length; i++) {
            int p = rand.nextInt(PRICES_HIGH[i] - PRICES_LOW[i] + 1) + PRICES_LOW[i];
            selling[i] = (int) (p * 1.1 * techLvl.getSelling()[i]); //TODO add govt weight
            buying[i] = (int) (p * techLvl.getBuying()[i]);

            if (selling[i] > 0) {
                goods[i] = rand.nextInt(rSize.getNum() / 2) + rSize.getNum() * 3 / 4;
            }
        }
    }

    /**
     * The getter method for x co-ordinate
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * The getter method for y co-ordinate
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * The getter method for region name
     * @return name of the region
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if the player has visited the game
     * @param v the boolean value
     */
    public void setVisited(boolean v) {
        visited = v;
    }

    /**
     * Checks if a region has been visited
     * @return the vistied region
     */
    public boolean getVisited() {
        return visited;
    }

    /**
     * The tech level
     * @return tech level
     */
    public TechLvl getTechLvl() {
        return techLvl;
    }

    /**
     * Get the description for region
     * @return the description
     */
    public String getDesc() {
        return desc;
    }

    /**
     * gets text name of size
     * @return rSize name
     */
    public String getSize() {
        return rSize.getName();
    }

    /**
     * gets available goods
     * @return goods array
     */
    public int[] getGoods() {
        return goods;
    }

    /**
     * gets selling table
     * @return array of prices
     */
    public int[] getSelling() {
        return selling;
    }

    /**
     * gets buying table
     * @return array of prices
     */
    public int[] getBuying() {
        return buying;
    }

    public void decrementGood(int goodID) {
        goods[goodID]--;
    }

    public int getFuelCost() {
        return fuelCost;
    }
}