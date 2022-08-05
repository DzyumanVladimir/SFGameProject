public class Skeleton extends Character{

    public Skeleton(int lvl){
        super(24, 1, 4, "Скелет " + lvl + " уровня", lvl, 10 + (int) (Math.random() * lvl));
    }

    public void lvlUp(){
        super.lvlUp(5, 5, 1);
    }
}
