public class Main {
    public static void main(String[] args) {
        System.out.println("=== Exercise 1: Queue ADT ===");

        System.out.println("\n-- ArrayListQueue --");
        QueueADT queue1 = new ArrayListQueue();
        queue1.enqueue(10);
        queue1.enqueue(20);
        queue1.enqueue(30);
        System.out.println("Size: " + queue1.size());          // 3
        System.out.println("Dequeue: " + queue1.dequeue());    // 10
        System.out.println("Dequeue: " + queue1.dequeue());    // 20
        System.out.println("isEmpty: " + queue1.isEmpty());    // false
        System.out.println("Dequeue: " + queue1.dequeue());    // 30
        System.out.println("isEmpty: " + queue1.isEmpty());    // true

        System.out.println("\n-- LinkedQueue (same client code, swapped impl) --");
        QueueADT queue2 = new LinkedQueue();  // only this line changes!
        queue2.enqueue(10);
        queue2.enqueue(20);
        queue2.enqueue(30);
        System.out.println("Size: " + queue2.size());          // 3
        System.out.println("Dequeue: " + queue2.dequeue());    // 10
        System.out.println("Dequeue: " + queue2.dequeue());    // 20
        System.out.println("isEmpty: " + queue2.isEmpty());    // false
        System.out.println("Dequeue: " + queue2.dequeue());    // 30
        System.out.println("isEmpty: " + queue2.isEmpty());    // true

        System.out.println("\n=== Exercise 2: OverdraftAccount ===");
        OverdraftAccount account = new OverdraftAccount();
        account.deposit(100);
        account.withdraw(50);
        account.withdraw(200);   // goes into overdraft (balance = -150)
        account.withdraw(400);   // would exceed -500 limit, denied
        System.out.println("Final balance: " + account.getBalance());
    }
}