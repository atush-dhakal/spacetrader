public class EnergyShield extends Shield {

    private int buyingPrice = 4450;
    private int sellingPrice = 3750;
    private String description = "The Energy Shield is a very basic deflector shield."
            + " Its operating principle is to absorb the energy directed at it.";

    public EnergyShield(int health) {
        super(100);
    }

    public int getBuyingPrice() {
        return buyingPrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public String getDescription() {
        return description;
    }
    //TODO
}
