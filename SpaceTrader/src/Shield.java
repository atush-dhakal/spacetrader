abstract class Shield {
    private int healthCapacity;
    private int health;
    private String imgPath;

    public Shield(int health) {
        this.health = health;
    }

    public int takeDamage(int damage) {
        if (health <= damage) {
            damage -= health;
            health = 0;
            return damage;
        } else {
            health -= damage;
            return 0;
        }
    }
}