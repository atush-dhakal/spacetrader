public class Player {
    private int[] skills; // Pilot, Fighter, Trader, Engineer
    private int credits;
    private Ship ship;
    private Region region;

    /**
     * The payer constructor
     * @param s the skills
     * @param c the credits
     */
    public Player(int[] s, int c) {
        skills = s;
        credits = c;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship s) {
        ship = s; //TODO allow item transfers
    }

    public void addCredits(int x) {
        credits += x;
    }

    public boolean removeCredits(int x) {
        if (credits >= x) { //if player has enough credits
            credits -= x;
            return true;
        }
        return false;
    }

    public int[] getSkills() {
        return skills;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

}