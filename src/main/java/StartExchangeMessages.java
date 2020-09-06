import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

public class StartExchangeMessages {

    private ConcurrentLinkedDeque<Map<Integer, String>> messagesQueue;

    public StartExchangeMessages() {
        messagesQueue = new ConcurrentLinkedDeque<Map<Integer, String>>();
        messagesQueue.add(new HashMap<Integer, String>() {{ put(0, "border"); }});

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
