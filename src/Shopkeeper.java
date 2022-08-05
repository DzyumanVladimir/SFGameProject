public class Shopkeeper {

    private String product;

    public Shopkeeper(){
        product = "Health potion";
    }

    public void menu(){
        System.out.println("1: " + product + ", цена - 20 золотых");
    }

    public String getProduct() {
        return product;
    }

    public boolean canSell(int gold, int price){
        if(gold < price){
            System.out.println(String.format("""
                    У тебя проблемы с деньгами?
                    Тебе не хватает %d - %d золотых
                    Возвращайся когда соберешь нужную сумму""", price, gold));
            return false;
        }
        System.out.println("\nСпасибо за покупку! Что-нибудь еще?");
        return true;
    }

}
