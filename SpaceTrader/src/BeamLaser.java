public class BeamLaser extends Weapon {

    private int buyingPrice = 11125;
    private int sellingPrice = 9375;
    private String description = "The Beam Laser is larger than the Pulse Laser, so can"
            + "build up enough charge to power what are essentially two Pulse Lasers. The"
            + "resulting effect appears more like a constant beam.";

    public BeamLaser() {
        super(25);
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
