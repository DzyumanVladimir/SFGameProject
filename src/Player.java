import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Player extends Character {
    private int exp, expForNextLvl; //текущий опыт и количество опыта, необходимое для повышения уровня
    private final Bag bag; //сумка для предметов
    private final BufferedReader reader;
    private String command; //команды для управления героем

    public Player(int hp, int agility, int damage, String name) {
        super(hp, agility, damage, name, 1, 1000);
        exp = 0;
        bag = new Bag();
        reader = new BufferedReader(new InputStreamReader(System.in));
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

    public int getBagSize() {
        return bag.bagSize();
    }

    public void openBag() {
        System.out.println("\nТы открыл рюкзак, а там...");
        if (getBagSize() == 0) {
            System.out.println("Пусто, в твоем рюкзаке мышь повесилась, надо бы что-нибудь прикупить...");
            System.out.println("0 - закрыть рюкзак");
            try {
                command = reader.readLine();
                while (!command.equals("0")) {
                    System.out.println("""
                            ==================================================================
                            Нужно нажать 0!""");
                    command = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String command;
            while (true) {
                bagInfo();
                System.out.println("""
                        Чтобы достать предмет - выбери его индекс
                        5 - выйти из меню""");
                try {
                    command = reader.readLine();
                    System.out.println("==================================================================");
                    if (command.equals("5"))
                        break;
                    try {
                        int index = Integer.parseInt(command) - 1;
                        if (index < 0 || index >= getBagSize()) {
                            System.out.println("Такого номера нет");
                        } else if (useItem())
                            takeFromBag(index);
                    } catch (NumberFormatException e) {
                        System.out.println("Введи номер предмета, который хочешь взять");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    public boolean useItem() {
        System.out.println("""
                0 - выбросить предмет
                1 - использовать предмет""");
        try {
            command = reader.readLine();
            System.out.println("==================================================================");
            switch (command) {
                case "0" -> System.out.println("\nТы выбросил предмет в кусты...Может быть стоило поискать мусорку?");
                case "1" -> {
                    if (getHp() == getMaxHP()) {
                        System.out.println("Ты полностью здоров, лучше это не пить");
                        return false;
                    } else
                        drinkPotion();
                }
                default -> {
                    System.out.println("Не та команда");
                    return useItem();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void lootMonster(Character character) {
        setGold(getGold() + character.getGold());
        exp += 10 + (character.getLvl() * 10);
        System.out.printf("""
                                
                Получено опыта: %d
                Получено золота: %d
                %n""", 10 + (character.getLvl() * 10), character.getGold());
        nextLvl();
    }

    public boolean putInBag(String thing) {
        if (getBagSize() < 4) {
            bag.putInBag(thing);
            return true;
        } else
            System.out.println("В рюкзаке не осталось места, нужно что-нибудь убрать");
        return false;
    }

    public void buy(int price) {
        setGold(getGold() - price);
    }

    public void drinkPotion() {
        heal(15);
        System.out.println("""
                Вы выпили какую-то жидкость, напоминающее зелье здоровья...
                Кажется вам стало лучше...""");
        if (getHp() > getMaxHP()) {
            int dif = getHp() - getMaxHP();
            heal(-dif);
        }
        System.out.println("Уровень здоровья теперь равен " + getHp() + "/" + getMaxHP());
    }

    public void takeFromBag(int index) {
        bag.takeFromBag(index);
    }

    public void info() {
        command = "";
        super.info();
        while (!command.equals("0")) {
            System.out.println("0 - закрыть меню");
            try {
                command = reader.readLine();
                System.out.println("==================================================================");
            } catch (IOException e) {
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
        System.out.printf("До следующего уровня осталось %d/%d\n%n", exp, expForNextLvl);
    }

    private class Bag {
        List<String> bag;

        Bag() {
            bag = new ArrayList<>();
        }

        void putInBag(String thing) {
            bag.add(thing);
            System.out.println(thing + " добавлено в рюкзак");
        }

        void takeFromBag(int index) {
            bag.remove(index);
        }

        void info() {
            int count = 1;
            for (String item : bag) {
                System.out.println(count + ": " + item);
                count++;
            }
            System.out.println("Занято " + bag.size() + "/4");
        }

        public int bagSize() {
            return bag.size();
        }
    }


}
