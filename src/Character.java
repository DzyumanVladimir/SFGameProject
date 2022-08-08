abstract public class Character {

    private int hp;
    private int agility;
    private int damage;
    private Character target;
    private final String name;
    private int lvl;
    private int gold;
    private int maxHP; //Максимальный уровень здоровья, фиксированный показатель, который растет с повышением уровня

    public Character(int hp, int agility, int damage, String name, int lvl, int gold) {
        if (hp <= 0 || agility < 0 || damage <= 0 || name == null || lvl <= 0 || gold < 0)
            throw new IllegalStateException();
        maxHP = hp;
        this.hp = maxHP;
        this.agility = agility;
        this.damage = damage;
        this.name = name;
        this.lvl = lvl;
        this.gold = gold;
    }

    public int getHp() {
        return hp;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getGold() {
        return gold;
    }

    public int getAgility() {
        return agility;
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }

    public int getLvl() {
        return lvl;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void takeTarget(Character character) { //Выбор цели для атаки
        target = character;
    }

    public void attack() {
        int missChance = (int) (Math.random() * target.agility); //вероятность промаха
        int critChance = (int) (Math.random() * 100); //вероятность критического удара
        if ((agility * 2) < missChance) {
            System.out.printf("%s уклонился от удара!\n%n", target.getName());
        } else {
            if (agility >= critChance) {
                target.hp -= damage * 2;
                System.out.printf(
                        "%s: Критический удар по %s, нанесено %d урона. Почему он все еще на ногах?\n%n",
                        getName(), target.getName(), damage * 2);
            } else {
                target.hp -= damage;
                System.out.printf("%s: Удар по %s, нанесено %d урона\n%n",
                        getName(), target.getName(), getDamage());
            }
        }
        System.out.printf("%s: осталось здоровья %d/%d\n%n", getName(), getHp(), getMaxHP());
        System.out.println("==================================================================");
    }

    public void info() {
        System.out.println(this);
    }

    public void lvlUp(int hp, int damage, int agility) {
        lvl++;
        maxHP += hp;
        this.damage += damage;
        this.agility += agility;
        this.hp = maxHP;
    }

    public void heal(int health) {
        hp += health;
    }

    @Override
    public String toString(){
        return name;
    }

}
