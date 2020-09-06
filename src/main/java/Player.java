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
//        String name = this.name;
//        String ins  = "";
//        for (int i = 0; i < this.capacityMessages; i++) {
//            String element = "'" + name + i + "'";
//            if (this.messagesQueue.size() % 2 == 1) {
//                this.messagesQueue.addFirst(element);
//                ins = "addFirst (" + element + ")";
//            } else {
//                this.messagesQueue.addLast(element);
//                ins = "addLast (" + element + ")";
//            }
//            System.out.println(name + ins
//                    + ": queue.size()="
//                    + this.messagesQueue.size());
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {}
//        }

        //String name = this.name;
        //String ins  = "";
        //for (int i = 0; i < this.capacityMessages; i++) {
        while (this.countOfMessagesSent < this.capacityMessages) {
            this.countOfMessagesSent++;
            //String element = "'" + this.name + i + "'"; // StringBuilder
            Map<Integer, String> message = new HashMap<>();
            message.put(this.countOfMessagesSent, "Hello! I love you! Would you tell me your name?");
            this.messagesQueue.addLast(message);


//            if (this.messagesQueue.size() % 2 == 1) {
//                this.messagesQueue.addFirst(element);
//                ins = "addFirst (" + element + ")";
//            } else {
//                this.messagesQueue.addLast(element);
//                ins = "addLast (" + element + ")";
//            }

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

            this.countOfMessagesReceive++;

            Map<Integer, String> message = this.messagesQueue.getFirst();
            ArrayList<Integer> senderKeys = new ArrayList<>(message.keySet());
            int firstSenderKey = senderKeys.get(0);
            String firstSenderMessage = message.get(firstSenderKey);

            if ((!firstSenderMessage.equals("border")) && this.countOfMessagesReceive == 10) {
                this.messagesQueue.stream().collect(Collectors.toList()).forEach(System.out::println);
                break;
            }

            Map<Integer, String> answer = new HashMap<>();
            StringBuffer answerMessage = new StringBuffer();
            answerMessage
                    .append(firstSenderMessage)
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

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}



//            if (firstSenderMessage.equals("border")) {
//
//                Map<Integer, String> answer = new HashMap<>();
//                StringBuffer answerMessage = new StringBuffer();
//                answerMessage
//                        .append(firstSenderMessage)
//                        .append(" count of receive messages is ")
//                        .append(this.countOfMessagesReceive);
//                answer.put(this.countOfMessagesReceive, answerMessage.toString());
//                this.messagesQueue.addFirst(answer);
//
//            } else {
//
//                if (firstSenderKey == 10) break;
//
//
//
//            }


        }


//        for (int i = 0; i < 10; i++) {
//            String text = "\n   receiver : queue.size()="
//                    + this.messagesQueue.size();
//            String element;
//            if (this.messagesQueue.size() % 2 == 1)
//                element = "pollFirst : "
//                        + this.messagesQueue.pollFirst();
//            else
//                element = "pollLast : "
//                        + this.messagesQueue.pollLast();
//            text += ", " + element;
//            System.out.println(text);
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {}
//        }
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
