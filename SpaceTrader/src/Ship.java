/**
 * This is the ship class
 */
public class Ship {
    private String name;
    private int cargoCapacity;
    private int cargoCount;
    private int[] cargo; // [Water,Furs,Food,Ore,Games,Fire,Med,Mach,Narc,Robot]
    private int fuelCapacity;
    private int fuel;
    private int healthCapacity;
    private int health;
    private Weapon[] weapons;
    private Shield[] shields;
    private Gadget[] gadgets;

    /**
     * detailed constructor for ship
     *
     * @param n name
     * @param cc cargo capacity
     * @param fc fuel capacity
     * @param hc health capacity
     * @param wc weapon capacity
     * @param sc shield capacity
     * @param gc gadget capacity
     */
    public Ship(String n, int cc, int fc, int hc, int wc, int sc, int gc) {
        name = n;
        cargoCapacity = cc;
        cargo = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        fuelCapacity = fc;
        fuel = fuelCapacity; // starts with a full tank
        healthCapacity = hc;
        health = healthCapacity; // starts at full hp

        weapons = new Weapon[wc];
        shields = new Shield[sc];
        gadgets = new Gadget[gc];
    }

    /**
     * getter for cargo contents
     * @return cargo array
     */
    public int[] getCargo() {
        return cargo;
    }

    /**
     * Set the fuel of the cargo
     * @param i the fuel to be set
     */
    public void setFuel(int i) {
        fuel = fuel - i;
    }

    /**
     * Get the fuel of the player's ship
     * @return the fuel of the ship
     */
    public int getFuel() {
        return fuel;
    }

    /**
     * Get the health of the ship
     * @return the health of the ship
     */
    public int getHealth() {
        return health;
    }

    /**
     * Set the health of the ship
     * @param n the health of the ship
     */
    public void setHealth(int n) {
        health = n;
    }

    /**
     * The item to add to the cargo
     * @param goodID the id of the cargo
     * @param amount the amount
     * @return true if item is added
     */
    public boolean addCargo(int goodID, int amount) {
        if (cargoCount + amount <= cargoCapacity) {
            cargo[goodID] += amount;
            cargoCount += amount;
            return true;
        }
        return false;
    }

    /**
     * Remove cargo
     * @param goodID the id of the good
     * @param amount the amount
     * @return if the item is removed
     */
    public boolean removeCargo(int goodID, int amount) {
        if (cargo[goodID] >= amount) {
            cargo[goodID] -= amount;
            cargoCount -= amount;
            return true;
        }
        return false;
    }

    /**
     * Attack the ship
     * @param target the ship of target
     */
    public void attack(Ship target) {
        int damage = 0;
        for (Weapon w : weapons) {
            damage += w.getDamage();
        }
        target.takeDamage(damage);
    }

    /**
     * The damage to the ship
     * @param damage the damage
     */
    public void takeDamage(int damage) {
        for (Shield s : shields) {
            if (s != null) {
                damage = s.takeDamage(damage);
            }
        }
        health = Math.max(health - damage, 0); // floor at 0
        if (health == 0) {
            Notification.show("MAYDAY", "Your ship has been destroyed! You die painfully in the"
                    + " unforgiving vacuum of space");
            //TODO
            System.exit(0);
        }
    }

    public int getCargoCount() {
        return cargoCount;
    }

    public void setCargoAtIndex(int index, int amount) {
        cargo[index] = amount;
    }

    public int getCargoAtIndex(int index) {
        return cargo[index];
    }

    public void emptyCargo() {
        for (int i = 0; i < cargo.length; i++) {
            cargo[i] = 0;
        }
        cargoCount = 0;
    }

    public int getCargoCapacity() {
        return cargoCapacity;
    }

    public boolean reduceFuel(int amount) {
        if (amount <= fuel) {
            fuel -= amount;
            return true;
        }
        return false;
    }

    public void addFuel(int amount) {
        fuel += amount;
    }

    public void refuel() {
        fuel = fuelCapacity;
    }

    public int getFuelCapacity() {
        return fuelCapacity;
    }
}