abstract class Weapon {
    protected int damage;
    protected String imgPath;

    public Weapon(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
