import java.util.ArrayList;
import java.util.List;

abstract public class Character{

    private int hp;
    private int agility;
    private int damage;
    private boolean isAlive;
    private Character target;
    private final String name;
    private int lvl;
    private int gold;


    public Character(int hp, int agility, int damage, String name, int lvl, int gold){
        if(hp <= 0 || agility < 0 || damage <= 0 || name == null || lvl <= 0 || gold < 0)
            throw new IllegalStateException();
        this.hp = hp;
        this.agility = agility;
        this.damage = damage;
        this.name = name;
        isAlive = true;
        this.lvl = lvl;
        this.gold = gold;
    }

    public int getHp() {
        return hp;
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

    public String getName(){
        return name;
    }

    public void takeTarget(Character character){
        target = character;
    }

    public Character getTarget(){
        return target;
    }

    public void attack(){
        if((agility * 3) - target.agility >= Math.random() * 100 + 1){
            System.out.println("MISS");
        }
        else {
            if(agility >= Math.random() * 100 + 1){
                target.hp -= damage * 2;
                System.out.println("Critical hit to " + target.name + ", " + damage * 2 + " damage!");
            }
            else
                target.hp -= damage;
            System.out.println("Hit to " + target.name + ", " + damage + " damage");
        }
    }

    @Override
    public String toString(){
        return "Name: " + name +
                "\nLevel: " + lvl +
                "\nHealth: " + hp +
                "\nDamage: " + damage +
                "\nAgility: " + agility;
    }

    public void info(){
        System.out.println(toString());
    }

    public void lvlUp(int hp, int damage, int agility){
        lvl++;
        hp += hp;
        damage += damage;
        agility += agility;
    }

    public void heal(int health){
        hp += health;
    }


}
