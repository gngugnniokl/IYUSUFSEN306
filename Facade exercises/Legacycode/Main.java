package Legacycode;

public class Main {
    public static void main(String[] args) {
        LegacyOrderFacade facade = new LegacyOrderFacade();
        facade.placeOrder("customer@example.com", "ITEM-42", 99.99, "123 Main St, Lagos, NG");
    }
}