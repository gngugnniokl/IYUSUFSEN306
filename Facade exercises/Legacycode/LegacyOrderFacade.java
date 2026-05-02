package Legacycode;

public class LegacyOrderFacade {

    private final LegacyOrderProcessor processor;

    public LegacyOrderFacade() {
        this.processor = new LegacyOrderProcessor();
    }

    public LegacyOrderFacade(LegacyOrderProcessor processor) {
        this.processor = processor;
    }

    public void placeOrder(String customerEmail, String itemCode,
                           double amount, String deliveryAddress) {
        processor.processOrder(customerEmail, itemCode, amount, deliveryAddress);
    }
}