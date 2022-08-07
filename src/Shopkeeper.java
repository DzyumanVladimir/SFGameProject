public class Shopkeeper {

    private final String product;

    public Shopkeeper() {
        product = "Зелье здоровья";
    }

    public void menu() {
        System.out.println("1: " + product + ", цена - 20 золотых");
    }

    public String getProduct() {
        return product;
    }

    public boolean canSell(int gold, int price) {
        if (gold < price) {
            System.out.printf("""
                    У тебя проблемы с деньгами?
                    Тебе не хватает %d золотых
                    Возвращайся когда соберешь нужную сумму
                    """, (price - gold));
            return false;
        }
        return true;
    }

}
