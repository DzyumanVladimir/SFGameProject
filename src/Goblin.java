public class Goblin extends Character{


    public Goblin(int lvl){
        super(17, 7, 2, "Гоблин " + lvl + " уровня", lvl, 10 + (int) (Math.random() * lvl));
    }

    public void lvlUp(){
        super.lvlUp(4, 2, 6);
    }
}
