import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Player extends Character {
    private int exp, expForNextLvl;
    private int lvl;
    private Bag bag;
    private BufferedReader reader;
    private String command;
    private int maxHP;

    public Player(int hp, int agility, int damage, String name) {
        super(hp, agility, damage, name, 1, 0);
        exp = 0;
        bag = new Bag();
        reader = new BufferedReader(new InputStreamReader(System.in));
        maxHP = hp;
        expForNextLvl = 100;
    }

    @Override
    public String toString() {
        return String.format("""
                Имя: %s
                Уровень: %d
                Здоровье: %d/%d
                Наносимый урон: %d
                Ловкость: %d
                Золото: %d
                Текущий уровень опыта: %d/%d
                """, getName(), getLvl(), getHp(), getMaxHP(), getDamage(), getAgility(), getGold(), exp, expForNextLvl);
    }

    public void openBag() throws IOException {
        System.out.println("\nТы открыл рюкзак, а там...");
        if(bag.bag.size() == 0){
            System.out.println("Пусто, в твоем рюкзаке мышь повесилась, надо бы что-нибудь прикупить...");
            System.out.println("0 - закрыть рюкзак");
            command = reader.readLine();
            while (!command.equals("0")){
                System.out.println("Нужно нажать 0!");
                command = reader.readLine();
            }
        }
        else {
            String thing = null;
            String command = "";
            while (!command.equals("4")) {
                System.out.println("""
                        Чтобы достать предмет - выбери его индекс
                        4 - выйти из меню""");
                bagInfo();
                command = reader.readLine();
                switch (command) {
                    case "0" -> {
                        thing = takeFromBag(0);
                    }
                    case "1" -> {
                        thing = takeFromBag(1);
                    }
                    case "2" -> {
                        thing = takeFromBag(2);
                    }
                    case "3" -> {
                        thing = takeFromBag(3);
                    }
                    default -> {
                        System.out.println("Ты не попал по кнопке, попробуй еще раз");
                    }
                }
            }
            if (thing != null) {
                useItem(thing);
            }
        }
    }

    public void useItem(String item) throws IOException {
        System.out.println("""
                    0 - выбросить предмет
                    1 - использовать предмет""");
        command = reader.readLine();
        switch (command) {
            case "0" -> {
                System.out.println("\nТы выбросил предмет в кусты...Может быть стоило поискать мусорку?");
            }
            case "1" -> {
                drinkPotion();
            }
            default -> {
                System.out.println("Не та команда");
                useItem(item);
            }
        }
    }

    public boolean isFreeSpaceBag() {
        return bag.bag.size() <= 4;
    }

    public void lootMonster(Character character) {
        setGold(getGold() + character.getGold());
        exp += 10 + (character.getLvl() * 10);
        System.out.println(String.format("""
                                
                Получено опыта: %d
                Получено золота: %d
                """, 10 + (character.getLvl() * 10), character.getGold()));
        nextLvl();
    }

    public boolean putInBag(String thing) {
        return bag.putInBag(thing);
    }

    public void buy(int price) {
        setGold(getGold() - price);
    }

    public void drinkPotion() {
        if (getHp() <= maxHP) {
            heal(15);
            System.out.println("""
                    Вы выпили какую-то жидкость, напоминающее зелье здоровья...
                    Кажется вам стало лучше...""");
            if (getHp() > maxHP) {
                int dif = getHp() - maxHP;
                heal(-dif);
            }
            System.out.println("\nУровень здоровья теперь равень " + getHp() + "/" + maxHP);
        } else
            System.out.println("\nТвой уровень здоровья равен " + getHp()
                    + "\nЗдоровее тебе уже не стать");
    }

    public String takeFromBag(int index) {
        return bag.takeFromBag(index);
    }

    public void info(){
        command = "";
        super.info();
        while (!command.equals("0")){
            System.out.println("0 - закрыть меню");
            try {
                command = reader.readLine();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void bagInfo() {
        bag.info();
    }

    public void isNewLvl() {
        if (exp >= expForNextLvl) {
            super.lvlUp(7, 4, 4);
            exp -= expForNextLvl;
            expForNextLvl += expForNextLvl / 2;
            System.out.println("Получен новый уровень!\n");
        }

    }

    public void nextLvl() {
        System.out.println(String.format("До следующего уровня осталось %d/%d\n", exp, expForNextLvl));
    }

    private class Bag {
        List<String> bag;

        Bag() {
            bag = new ArrayList<>();
        }

        boolean putInBag(String thing) {
            if (bag.size() > 4) {
                return false;
            } else {
                bag.add(thing);
                return true;
            }
        }

        String takeFromBag(int index) {
            return bag.remove(index);
        }

        void info() {
            bag.forEach(thing ->
                    System.out.println(bag.indexOf(thing) + ": " + thing)
            );
            System.out.println("Занято " + bag.size() + "/4");
        }
    }


}
