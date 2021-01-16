public class PulseLaser extends Weapon {

    private int buyingPrice = 1780;
    private int sellingPrice = 1500;
    private String description = "The Pulse Laser is the weakest weapon available. "
            + "Its small size allows only enough energy to build up to emit pulses of"
            + " light.";

    public PulseLaser() {
        super(15);
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
