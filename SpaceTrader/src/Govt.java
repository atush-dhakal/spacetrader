public enum Govt {
    ANARCHY("Anarchy", //TODO all these nums are placeholders
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            1.0, 1.0, 1.0),
    CAPITALIST_STATE("Capitalist State",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            1.0, 1.0, 1.0),
    COMMUNIST_STATE("Communist State",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            1.0, 1.0, 1.0),
    CONFEDERACY("Confederacy",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            1.0, 1.0, 1.0),
    CORPORATE_STATE("Corporate State",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            1.0, 1.0, 1.0),
    CYBERNETIC_STATE("Cybernetic State",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            1.0, 1.0, 1.0),
    DEMOCRACY("Democracy",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            1.0, 1.0, 1.0),
    DICTATORSHIP("Dictatorship",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            1.0, 1.0, 1.0),
    FASCIST_STATE("Fascist State",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            1.0, 1.0, 1.0),
    FEUDAL_STATE("Feudal State",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            1.0, 1.0, 1.0),
    MILITARY_STATE("Military State",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            1.0, 1.0, 1.0),
    MONARCHY("Monarchy",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            1.0, 1.0, 1.0),
    PACIFIST_STATE("Pacifist State",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            1.0, 1.0, 1.0),
    SOCIALIST_STATE("Socialist State",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            1.0, 1.0, 1.0),
    STATE_OF_SATORI("State of Satori",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            1.0, 1.0, 1.0),
    TECHNOCRACY("Technocracy",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            1.0, 1.0, 1.0),
    THEOCRACY("Theocracy",
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            1.0, 1.0, 1.0);

    private String name;
    private double[] selling; // [Water,Furs,Food,Ore,Games,Fire,Med,Mach,Narc,Robot]
    private double[] buying; //see above
    private double police; //police activity weight
    private double pirates; //piracy weight
    private double crime; //crime level weight

    Govt(String n, double[] s, double[] b, double pol, double pir, double c) {
        name = n;
        selling = s;
        buying = b;
        police = pol;
        pirates = pir;
        crime = c;
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

    public double getPolice() {
        return police;
    }

    public double getPirates() {
        return pirates;
    }

    public double getCrime() {
        return crime;
    }

}
