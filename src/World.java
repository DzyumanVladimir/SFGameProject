import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class World {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        System.out.println("""
                Добро пожаловать в лучшую игру на земле!
                Придумай имя для своего персонажа и давай уже начинать играть!""");
        String name = null;
        System.out.print("Введите имя: ");
        try {
            name = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Player player = new Player(100, 4, 8, name);
        Shopkeeper shopkeeper = new Shopkeeper();
        System.out.println("\nУдачи " + name);
        String command = "";
        while (!command.equals("4") && player.isAlive()) {
            System.out.println("""
                    ==================================================================                    
                    Выберите маршрут:
                    1 - Тропа к торговцу
                    2 - Тропа в темный лес
                    3 - Информация о персонаже
                    4 - Выйти из игры
                    ==================================================================""");
            try {
                command = reader.readLine();
                switch (command) {
                    case "1" -> shop(player, shopkeeper);
                    case "2" -> {
                        System.out.println("""
                                Опасный ты выбрал маршрут
                                                                    
                                Вы оказались в глухом темном лесу
                                """);
                        forest(player);
                    }
                    case "3" -> player.info();
                    case "4" -> System.out.println("Пока!");
                    default -> System.out.println("Данная команда отсутствует в функционале");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void shop(Player player, Shopkeeper shopkeeper) throws IOException {
        System.out.println("""
                ==================================================================
                Добро пожаловать в лавку торговца!
                Чем могу быть полезен?""");
        String command = "";
        while (!command.equals("0")) {
            System.out.println("""
                    0 - уйти
                    2 - открыть рюкзак
                                    
                    Вот наш ассортимент, выбирай что нравится, только не забудь заплатить!""");
            shopkeeper.menu();
            command = reader.readLine();
            System.out.println("==================================================================");
            switch (command) {
                case "1" -> {
                    String product = shopkeeper.getProduct();
                    if (shopkeeper.canSell(player.getGold(), 20) && player.putInBag(product)) {
                        player.buy(20);
                        System.out.println("Спасибо за покупку, что-нибудь еще?");
                    }
                }
                case "2" -> player.openBag();
                case "0" -> System.out.println("Удачи!");
                default -> System.out.println("Такого товара нет, посмотри еще раз");
            }
        }
    }


    public static void forest(Player player) throws IOException {
        String command = "";
        System.out.println("""
                Лучше здесь не задерживаться, кажется поблизости кто-то есть...
                """);
        List<Character> monsters = createMonsters(player);
        while (!command.equals("3") && player.isAlive()) {
            if (monsters.size() == 0) {
                monsters = createMonsters(player);
            }
            monsters.forEach(System.out::println);
            System.out.println("""
                                        
                    Что будем делать?
                                        
                    1 - Выбрать цель и атаковать
                    2 - Поискать других противников
                    3 - Уйти из леса
                    4 - Открыть рюкзак
                    5 - Показать информацию о персонаже""");
            command = reader.readLine();
            System.out.println("==================================================================");
            switch (command) {
                case "1" -> {
                    Character enemy = chooseEnemy(monsters);
                    if (enemy == null) {
                        System.out.println("""
                                Лучшая драка та, которую удалось избежать!
                                ==================================================================""");
                    } else
                        fight(player, enemy);
                }
                case "2" -> monsters = createMonsters(player);
                case "3" -> System.out.println("""
                        Мудрое решение
                        Лучше убраться отсюда поскорее""");
                case "4" -> player.openBag();
                case "5" -> player.info();
                default -> System.out.println("""
                        Не та кнопка, попробуй еще""");
            }
        }
    }

    public static List<Character> createMonsters(Player player) {
        int monstersCount = (int) (Math.random() * 5) + 1;
        List<Character> monsters = new ArrayList<>();
        for (int i = 0; i < monstersCount; i++) {
            int random = (int) (Math.random() * 2);
            if (random == 0) {
                monsters.add(new Goblin((int) (Math.random() * (player.getLvl() + 3)) + 1));
            } else
                monsters.add(new Skeleton((int) (Math.random() * (player.getLvl() + 3)) + 1));
        }
        return monsters;
    }

    public static Character chooseEnemy(List<Character> monsters) throws IOException {
        System.out.println("""
                Выберите цель
                                            
                0: Уйти
                """);
        int count = 1;
        int index;
        for (Character monster : monsters) {
            System.out.println(count + ": " + monster);
            count++;
        }
        String command = reader.readLine();
        System.out.println("==================================================================");
        if (command.equals("0")) {
            return null;
        }
        try {
            index = Integer.parseInt(command) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Приятель, нужно вводить цифры!\n");
            return chooseEnemy(monsters);
        }
        try {
            return monsters.remove(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Эта кнопка не подходит\n");
            return chooseEnemy(monsters);
        }

    }

    public static void fight(Player player, Character monster) {
        System.out.println("""
                Кажется противник вас заметил!""");
        player.takeTarget(monster);
        monster.takeTarget(player);
        Thread playerAttackThread = new Thread(() -> {
            int attackSpeed = player.getAgility() * 10;
            if (attackSpeed > 1000)
                attackSpeed = 1000;
            while (player.getHp() > 0 && monster.getHp() > 0) {
                player.isNewLvl();
                player.attack();
                try {
                    Thread.sleep(2000 - attackSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread monsterAttackThread = new Thread(() -> {
            int attackSpeed = monster.getAgility() * 10;
            if (attackSpeed > 1000)
                attackSpeed = 1000;
            while (player.getHp() > 0 && monster.getHp() > 0) {
                monster.attack();
                try {
                    Thread.sleep(2000 - attackSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        playerAttackThread.start();
        monsterAttackThread.start();

        try {
            playerAttackThread.join();
            monsterAttackThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (player.isAlive()) {
            player.lootMonster(monster);
            player.isNewLvl();
        } else {
            System.out.println("""
                    Вас убили
                    В другой раз все получится""");
        }

    }
}


