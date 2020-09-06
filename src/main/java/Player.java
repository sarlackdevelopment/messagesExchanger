import java.util.concurrent.ConcurrentLinkedDeque;

public class Player implements Runnable {

    private String name;
    private int capacityMessages;
    private ConcurrentLinkedDeque<String>  messagesQueue;

    public Player(String name, int capacityMessages, ConcurrentLinkedDeque<String> messagesQueue) {
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

    public ConcurrentLinkedDeque<String> getMessagesQueue() {
        return messagesQueue;
    }

    public void setMessagesQueue(ConcurrentLinkedDeque<String> messagesQueue) {
        this.messagesQueue = messagesQueue;
    }

    public void send() {
        System.out.println("Producer started");
        String name = this.name;
        String ins  = "";
        for (int i = 0; i < this.capacityMessages; i++) {
            String element = "'" + name + i + "'";
            if (this.messagesQueue.size() % 2 == 1) {
                this.messagesQueue.addFirst(element);
                ins = "addFirst (" + element + ")";
            } else {
                this.messagesQueue.addLast(element);
                ins = "addLast (" + element + ")";
            }
            System.out.println(name + ins
                    + ": queue.size()="
                    + this.messagesQueue.size());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        }
    }

    public void receive() {
        System.out.println("Receiver started");
        for (int i = 0; i < 10; i++) {
            String text = "\n   receiver : queue.size()="
                    + this.messagesQueue.size();
            String element;
            if (this.messagesQueue.size() % 2 == 1)
                element = "pollFirst : "
                        + this.messagesQueue.pollFirst();
            else
                element = "pollLast : "
                        + this.messagesQueue.pollLast();
            text += ", " + element;
            System.out.println(text);
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
