import java.util.concurrent.ConcurrentLinkedDeque;

public class StartExchangeMessages {

    private ConcurrentLinkedDeque<String> messagesQueue;

    public StartExchangeMessages() {
        messagesQueue = new ConcurrentLinkedDeque<String>();

        Thread initiator = new Thread(new Player("initiator", 10, messagesQueue));
        initiator.start();

        Thread receiver = new Thread(new Player("receiver", 10, messagesQueue));
        receiver.start();

        while (receiver.isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        new StartExchangeMessages();
    }
}
