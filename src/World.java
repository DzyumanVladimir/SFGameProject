import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class World {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Привет! Придумай имя для твоего персонажа");
        String name = reader.readLine();
        Player player = new Player(100, 4, 8, name);
        Shopkeeper shopkeeper = new Shopkeeper();
        System.out.println("Удачи " + name);
        String command = "";
        while (!command.equals("3")) {
            System.out.println("""
                    Добро пожаловать в лучшую игру на земле
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
                            Ассортимент у нас пока не большой""");
                    shop(player, shopkeeper);
                    break;
                }
                case "2" -> {
                    System.out.println("\nОпасный ты выбрал маршрут, кажется поблизости кто-то есть");
                    forest(player);
                }
            }
        }
    }

    public static void shop(Player player, Shopkeeper shopkeeper) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("""
        Если захочешь уйти, нажми - 0
        Вот тебе меню, выбирай что нравится, только заплати!""");
        shopkeeper.menu();
        String command = reader.readLine();
        switch (command) {
            case "1" -> {
                String product = shopkeeper.getProduct();
                if (player.isFreeSpaceBag()){
                    if(shopkeeper.canSell(player.getGold(), 20)){
                        player.buy(20);
                        player.putInBag(product);
                        shop(player,shopkeeper);
                    }
                }
                else {
                    System.out.println("\nКажется в рюкзаке закончилось место");
                    player.lookInBag();
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



    public static void forest(Player player) {

    }

    /*public static void bagIsFul(Player player) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command;
        System.out.println("Похоже в рюкзаке не осталось места");
        command = reader.readLine();
        System.out.println("0 - уйти из магазина" +
                "\n1 - заглянуть в рюкзак");
        switch (command){
            case "0" -> {
                break;
            }
            case "1" -> {
                lookInBag(player);
                break;
            }
            default -> {
                System.out.println("Ты не попал по кнопке, попробуй еще раз");
                bagIsFul(player);
            }
        }
    }*/
}
