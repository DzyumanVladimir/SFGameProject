public class Skeleton extends Character {

    public Skeleton(int lvl) {
        super(24, 1, 7, "Скелет " + lvl + " уровня", 1, 10);
        for (int i = 1; i < lvl; i++) {
            lvlUp();
        }
    }

    public void lvlUp() {
        super.lvlUp(5, 5, 1);
        setGold(getGold() + ((int) ((Math.random() + 1) * getLvl()))); //количество золота у противника зависит от его уровня
    }

    @Override
    public String toString() {
        return String.format("""
                Скелет %d уровня""", getLvl());
    }
}
