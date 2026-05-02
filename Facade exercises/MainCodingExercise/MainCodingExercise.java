import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Inventory {
    boolean checkStock(String productId) { return true; }
    void reserve(String productId) { System.out.println("Reserved " + productId); }
    void release(String productId) { System.out.println("Released " + productId); }
}

class Payment {
    boolean charge(String userId, double amount) { return true; }
    void refund(String userId, double amount) { System.out.println("Refunded " + amount); }
}

class Shipping {
    String createLabel(String address) { return "TRK" + System.currentTimeMillis(); }
    void schedulePickup(String label) { System.out.println("Pickup scheduled for " + label); }
    boolean isAvailable() { return true; }
}

class Email {
    void send(String to, String subject, String body) {
        System.out.println("Email to " + to + " | Subject: " + subject);
        System.out.println("Body:\n" + body);
    }
}

class TaxCalculator {
    double computeTax(String state, double price) {
        if ("CA".equalsIgnoreCase(state)) {
            return price * 0.08;
        }
        return 0.0;
    }
}

class Logger {
    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    void log(String userId, boolean success) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String status    = success ? "SUCCESS" : "FAILURE";
        System.out.println("[LOG] " + timestamp + " | User: " + userId + " | " + status);
    }
}

class OrderResult {
    private final boolean success;
    private final String trackingNumber;
    private final String message;

    public OrderResult(boolean success, String trackingNumber, String message) {
        this.success        = success;
        this.trackingNumber = trackingNumber;
        this.message        = message;
    }

    public boolean isSuccess()        { return success; }
    public String getTrackingNumber() { return trackingNumber; }
    public String getMessage()        { return message; }

    @Override
    public String toString() {
        return "OrderResult{" +
               "success=" + success +
               ", trackingNumber='" + trackingNumber + "'" +
               ", message='" + message + "'" +
               "}";
    }
}

class CheckoutFacade {

    private final Inventory     inventory;
    private final Payment       payment;
    private final Shipping      shipping;
    private final Email         email;
    private final TaxCalculator taxCalculator;
    private final Logger        logger;

    public CheckoutFacade() {
        this.inventory     = new Inventory();
        this.payment       = new Payment();
        this.shipping      = new Shipping();
        this.email         = new Email();
        this.taxCalculator = new TaxCalculator();
        this.logger        = new Logger();
    }

    public OrderResult checkout(String userId, String productId,
                                double price, String address) {

        System.out.println("\n--- Starting checkout for " + userId + " ---");

        String state = parseState(address);
        double tax   = taxCalculator.computeTax(state, price);
        double total = price + tax;
        System.out.printf("Tax (%s): $%.2f | Total: $%.2f%n", state, tax, total);

        if (!inventory.checkStock(productId)) {
            logger.log(userId, false);
            return new OrderResult(false, null, "Out of stock: " + productId);
        }

        inventory.reserve(productId);

        if (!payment.charge(userId, total)) {
            inventory.release(productId);
            logger.log(userId, false);
            return new OrderResult(false, null, "Payment failed for user: " + userId);
        }
        System.out.printf("Charged $%.2f to %s%n", total, userId);

        if (!shipping.isAvailable()) {
            payment.refund(userId, total);
            inventory.release(productId);
            logger.log(userId, false);
            return new OrderResult(false, null, "Shipping unavailable");
        }

        String trackingNumber = shipping.createLabel(address);
        shipping.schedulePickup(trackingNumber);

        String body = String.format(
            "Hi %s, your order for %s has been placed!%n" +
            "  Subtotal  : $%.2f%n" +
            "  Tax (%s)  : $%.2f%n" +
            "  Total     : $%.2f%n" +
            "  Tracking  : %s",
            userId, productId, price, state, tax, total, trackingNumber
        );
        email.send(userId, "Order Confirmed!", body);

        logger.log(userId, true);

        return new OrderResult(true, trackingNumber,
                               "Order placed successfully for " + productId);
    }

    private String parseState(String address) {
        String[] parts = address.split(",");
        if (parts.length >= 2) {
            return parts[parts.length - 1].trim();
        }
        return "UNKNOWN";
    }
}


class Main {
    public static void main(String[] args) {

        CheckoutFacade facade = new CheckoutFacade();

        System.out.println("========== TEST 1: California User ==========");
        OrderResult r1 = facade.checkout(
            "user123", "PROD-456", 49.99, "123 Lagos Street, CA"
        );
        System.out.println(r1);

        System.out.println();

        System.out.println("========== TEST 2: New York User ==========");
        OrderResult r2 = facade.checkout(
            "user456", "PROD-789", 99.99, "45 Eko Avenue, NY"
        );
        System.out.println(r2);
    }
}