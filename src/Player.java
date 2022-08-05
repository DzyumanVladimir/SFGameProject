import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Player extends Character{
    private int exp;
    private int lvl;
    private Bag bag;
    private BufferedReader reader;
    private String command;
    private int maxHP;

    public Player(int hp, int agility, int damage, String name){
        super(hp, agility, damage, name, 1, 0);
        exp = 0;
        bag = new Bag();
        reader = new BufferedReader(new InputStreamReader(System.in));
        maxHP = 100;
    }

    public void lookInBag()throws IOException {
        System.out.println("\n�� ������ ������, � ���...");
        bagInfo();
        System.out.println("""
                ����� ������� ������� - ������ ��� ������
                4 - ����� �� ����""");
        String command = reader.readLine();
        String thing;
        switch (command){
            case "0" -> {
                thing = takeFromBag(0);
                break;
            }
            case "1" -> {
                thing = takeFromBag(1);
                break;
            }
            case "2" -> {
                thing = takeFromBag(2);
                break;
            }
            case "3" -> {
                thing = takeFromBag(3);
                break;
            }
            case "4" -> {
                break;
            }
            default -> {
                System.out.println("�� �� ����� �� ������, �������� ��� ���");
                lookInBag();
            }
        }
        System.out.println("""
                0 - ��������� �������
                1 - ������������ �������""");
        command = reader.readLine();
        switch (command){
            case "0" -> {
                System.out.println("\n�� �������� ������� � �����...����� ���� ������ �������� �������?");
            }
            case "1" -> {
                drinkPotion();
            }
        }
    }

    public boolean isFreeSpaceBag(){
        return bag.bag.size() <= 4;
    }

    public boolean putInBag(String thing){
        return bag.putInBag(thing);
    }
    
    public void buy(int price){
        setGold(getGold() - price);
    }

    public void drinkPotion(){
        if (getHp() <= maxHP) {
            heal(15);
            System.out.println("""
                    �� ������ �����-�� ��������, ������������ ����� ��������...
                    ������� ��� ����� �����...""");
            if(getHp() > maxHP){
                int dif = getHp() - maxHP;
                heal(-dif);
            }
            System.out.println("\n������� �������� ������ ������ " + getHp());
        }
        else
            System.out.println("\n���� ������� �������� ����� " + getHp()
            + "\n�������� ���� ��� �� �����");
    }

    public String takeFromBag(int index){
        return bag.takeFromBag(index);
    }

    public void bagInfo(){
        bag.info();
    }

    public void lvlUp(){
        super.lvlUp(maxHP + 7, 4, 4);
    }

    private class Bag{
        List<String> bag;

        Bag(){
            bag = new ArrayList<>();
        }

        boolean putInBag(String thing){
            if(bag.size() > 4){
                return false;
            }
            else {
                bag.add(thing);
                return true;
            }
        }

        String takeFromBag(int index){
            return bag.remove(index);
        }

        void info(){
            bag.forEach(thing -> 
                System.out.println(bag.indexOf(thing) + ": " + thing)
            );
            System.out.println("������ " + bag.size() + "/4");        
        }
    }


}
