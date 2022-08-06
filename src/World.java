import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class World {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("""
                Привет! Добро пожаловать в лучшую игру на земле
                Придумай имя для своего персонажа и давай уже начинать играть!""");
        String name = reader.readLine();
        Player player = new Player(100, 4, 8, name);
        Shopkeeper shopkeeper = new Shopkeeper();
        System.out.println("\nУдачи " + name);
        String command = "";
        while (!command.equals("4") && player.isAlive()) {
            System.out.println("""
                                        
                    Выбор маршрутов у тебя небольшой:
                    1 - Пойти на шоппинг к торговцу, озон и доставку еще не придумали
                    2 - Пойти в темный лес на разборки с гоблинами и скелетами
                    3 - Информация о персонаже
                    4 - Выйти из игры""");
            command = reader.readLine();
            switch (command) {
                case "1" -> {
                    System.out.println("""
                            Добро пожаловать в лавку торговца!
                            Чем могу быть полезен?""");
                    shop(player, shopkeeper);
                }
                case "2" -> {
                    System.out.println("""
                            Опасный ты выбрал маршрут
                                                                
                            Вы оказались в глухом темном лесу
                            """);
                    forest(player);
                }
            }
        }
    }

    public static void shop(Player player, Shopkeeper shopkeeper) throws IOException {
        System.out.println("""
                Если захочешь уйти, нажми - 0
                Вот наш ассортимент, выбирай что нравится, только не забудь заплатить!""");
        shopkeeper.menu();
        String command = reader.readLine();
        switch (command) {
            case "1" -> {
                String product = shopkeeper.getProduct();
                if (player.isFreeSpaceBag()) {
                    if (shopkeeper.canSell(player.getGold(), 20)) {
                        player.buy(20);
                        player.putInBag(product);
                        shop(player, shopkeeper);
                    }
                } else {
                    System.out.println("\nКажется в рюкзаке закончилось место");
                    player.openBag();
                    shop(player, shopkeeper);
                }
            }
            case "0" -> {
                System.out.println("Удачи!");
            }
            default -> {
                System.out.println("Такого товара нет, посмотри еще раз");
                shop(player, shopkeeper);
            }
        }
    }


    public static void forest(Player player) throws IOException, InterruptedException {
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
            switch (command) {
                case "1" -> {
                    Character enemy = chooseEnemy(monsters);
                    if (enemy == null) {
                        System.out.println("""
                                Лучшая драка та, которую удалось избежать!""");
                    } else
                        fight(player, enemy);
                }
                case "2" -> {
                    monsters = createMonsters(player);
                }
                case "3" -> {
                    System.out.println("""
                            Мудрое решение
                            Лучше убраться отсюда поскорее""");
                }
                case "4" -> {
                    player.openBag();
                }
                case "5" -> {
                    player.info();
                }
                default -> {
                    System.out.println("""
                            Не та кнопка, попробуй еще""");
                }
            }
        }
    }

    public static List<Character> createMonsters(Player player) {
        int monstersCount = (int) (Math.random() * 5) + 1;
        List<Character> monsters = new ArrayList<>();
        for (int i = 0; i < monstersCount; i++) {
            if (((int) (Math.random() * 10) + 1) <= 5) {
                monsters.add(new Goblin((int) (Math.random() * (player.getLvl() + 3)) + 1));
            } else
                monsters.add(new Skeleton((int) (Math.random() * player.getLvl()) + 4));
        }
        return monsters;
    }

    public static Character chooseEnemy(List<Character> monsters) throws IOException {
        System.out.println("""
                Выберите цель
                                            
                0: Уйти
                """);
        int count = 1;
        int index = 0;
        for (Character monster : monsters) {
            System.out.println(count + ": " + monster);
            count++;
        }
        String command = reader.readLine();
        if (command.equals("0")) {
            return null;
        }
        try {
            index = Integer.parseInt(command) - 1;
        }
        catch (NumberFormatException e){
            System.out.println("Приятель, нужно вводить цифры!\n");
            return chooseEnemy(monsters);
        }
        try{
            return monsters.remove(index);
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Эта кнопка не подходит\n");
            return chooseEnemy(monsters);
        }

    }

    public static void fight(Player player, Character character) throws InterruptedException {
        System.out.println("""
                Кажется противник вас заметил!""");
        player.takeTarget(character);
        character.takeTarget(player);
        Thread t1 = new Thread(() -> {
            while (player.getHp() > 0 && character.getHp() > 0) {
                player.isNewLvl();
                player.attack();
                try {
                    Thread.sleep(2000 - (player.getAgility() * 10L));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while (player.getHp() > 0 && character.getHp() > 0) {
                character.attack();
                try {
                    Thread.sleep(2000 - (character.getAgility() * 10L));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        if (player.isAlive()) {
            player.lootMonster(character);
            player.isNewLvl();
        } else {
            System.out.println("""
                    Вас убили
                    В другой раз все получится""");
        }

    }
}


