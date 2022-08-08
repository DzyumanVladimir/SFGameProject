public class Goblin extends Character {

    public Goblin(int lvl) {
        super(17, 7, 3, "Гоблин " + lvl + " уровня", 1, 10);
        for (int i = 1; i < lvl; i++) {
            lvlUp();
        }
    }

    public void lvlUp() {
        super.lvlUp(4, 2, 6);
        setGold(getGold() + ((int) ((Math.random() + 1) * getLvl()))); //количество золота у противника зависит от его уровня
    }
}
