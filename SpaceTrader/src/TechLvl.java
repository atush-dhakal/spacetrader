import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum TechLvl {
    PRE_AGRICULTURAL("Pre-Agricultural",
            new double[] {.9, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            new double[] {.7, 1, .7, 0, 0, 0, 0, 0, 1, 0}),
    AGRICULTURAL("Agricultural",
            new double[] {1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
            new double[] {1, 1, 1, 0, 1, 1, 1, 0, 1, 0}),
    MEDIEVAL("Medieval",
            new double[] {1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
            new double[] {1, 1, 1, 1, 1, 1, 1, 0, 1, 0}),
    RENAISSANCE("Renaissance",
            new double[] {1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 0}),
    EARLY_INDUSTRIAL("Early Industrial",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}),
    INDUSTRIAL("Industrial",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}),
    POST_INDUSTRIAL("Post-Industrial",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}),
    HI_TECH("Hi-Tech",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1});

    private static final List<TechLvl> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    /**
     * Tech level in a region
     * @return the tech level
     */
    public static TechLvl randomLvl()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    private String name;
    private double[] selling; // [Water,Furs,Food,Ore,Games,Fire,Med,Mach,Narc,Robot]
    private double[] buying;

    TechLvl(String n, double[] s, double[] b) {
        name = n;
        selling = s;
        buying = b;
    }

    public String getName() {
        return name;
    }

    public double[] getSelling() {
        return selling;
    }

    public double[] getBuying() {
        return buying;
    }
}
