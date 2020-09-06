import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

public class Player implements Runnable {

    private String name;
    private int capacityMessages;
    private ConcurrentLinkedDeque<Map<Integer, String>>  messagesQueue;

    private int countOfMessagesSent = 0;
    private int countOfMessagesReceive = 0;

    public Player(String name, int capacityMessages, ConcurrentLinkedDeque<Map<Integer, String>> messagesQueue) {
        this.name = name;
        this.capacityMessages = capacityMessages;
        this.messagesQueue = messagesQueue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacityMessages() {
        return capacityMessages;
    }

    public void setCapacityMessages(int capacityMessages) {
        this.capacityMessages = capacityMessages;
    }

    public ConcurrentLinkedDeque<Map<Integer, String>> getMessagesQueue() {
        return messagesQueue;
    }

    public void setMessagesQueue(ConcurrentLinkedDeque<Map<Integer, String>> messagesQueue) {
        this.messagesQueue = messagesQueue;
    }

    public void send() {
        System.out.println("Producer started");

        while (this.countOfMessagesSent < this.capacityMessages) {

            this.countOfMessagesSent++;

            Map<Integer, String> message = new HashMap<>();
            message.put(this.countOfMessagesSent, "Hello! I love you! Would you tell me your name?");
            this.messagesQueue.addLast(message);

            StringBuffer logMessage = new StringBuffer();
            logMessage
                    .append(this.name)
                    .append(" sent message №")
                    .append(this.countOfMessagesSent);

            System.out.println(logMessage);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        }


    }

    public void receive() {
        System.out.println("Receiver started");
        while (true) {

            if (this.messagesQueue.isEmpty()) continue;

            Map<Integer, String> firstMessage = this.messagesQueue.getFirst();
            ArrayList<Integer> firstKeys = new ArrayList<>(firstMessage.keySet());
            int firstSenderKey = firstKeys.get(0);
            String firstSenderMessage = firstMessage.get(firstSenderKey);

            if ((firstSenderMessage.equals("border")) && this.messagesQueue.size() <= 1) continue;

            Map<Integer, String> lastMessage = this.messagesQueue.getLast();
            ArrayList<Integer> lastKeys = new ArrayList<>(lastMessage.keySet());
            int lastSenderKey = lastKeys.get(0);
            String lastSenderMessage = lastMessage.get(lastSenderKey);

            if (lastSenderKey <= this.countOfMessagesReceive) continue;

            Map<Integer, String> answer = new HashMap<>();
            StringBuffer answerMessage = new StringBuffer();
            answerMessage
                    .append(lastSenderMessage)
                    .append(" count of receive messages is ")
                    .append(this.countOfMessagesReceive);
            answer.put(this.countOfMessagesReceive, answerMessage.toString());
            this.messagesQueue.addFirst(answer);

            StringBuffer logMessage = new StringBuffer();
            logMessage
                    .append(this.name)
                    .append(" receive message №")
                    .append(this.countOfMessagesReceive);

            System.out.println(logMessage);

            this.countOfMessagesReceive++;

            if (this.countOfMessagesReceive == 10) {
                this.messagesQueue.stream().collect(Collectors.toList()).forEach(System.out::println);
                break;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}

        }

    }

    @Override
    public void run() {

        switch (this.name) {
            case ("initiator"):
                this.send();
                break;
            case ("receiver"):
                this.receive();
                break;
        }


    }

}
