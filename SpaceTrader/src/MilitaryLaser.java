public class MilitaryLaser extends Weapon {

    private int buyingPrice = 31150;
    private int sellingPrice = 26250;
    private String description = "The Military Laser is the largest commercially"
            + " available weapon. It can build up enough charge to power three Pulse"
            + " Lasers in series, resulting in a more dense and concentrated beam.";

    public MilitaryLaser() {
        super(35);
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
