package Legacycode;

class Inventory {
    public boolean checkStock(String itemCode) { return true; }
    public void reserve(String itemCode) {}
}

class Payment {
    public boolean charge(String email, double amount) { return true; }
}

class Shipping {
    public String createLabel(String address) { return "LABEL-" + address; }
    public void schedulePickup(String label) {}
}

class Email {
    public void send(String to, String subject, String body) {}
}