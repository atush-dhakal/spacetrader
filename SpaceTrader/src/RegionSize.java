import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum RegionSize {
    TINY("Tiny", 10),
    SMALL("Small", 20),
    MEDIUM("Medium", 40),
    LARGE("Large", 60),
    EXPANSIVE("Expansive", 80);

    private String name;
    private int num; // avg number of each good available

    private static final List<RegionSize> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    /**
     * rand size in a region
     * @return a size
     */
    public static RegionSize randomSize()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    RegionSize(String na, int nu) {
        name = na;
        num = nu;
    }

    /**
     * getter for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for number scale
     * @return num
     */
    public int getNum() {
        return num;
    }
}
