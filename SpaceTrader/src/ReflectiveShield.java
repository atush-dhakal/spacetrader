public class ReflectiveShield extends Shield {

    private int buyingPrice = 17800;
    private int sellingPrice = 15000;
    private String description = "The Reflective Shield is twice as powerful as the"
            + "Energy Shield. It works by reflecting the energy directed at it instead"
            + " of absorbing that energy.";

    public ReflectiveShield() {
        super(200);
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
