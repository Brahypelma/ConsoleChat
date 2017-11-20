import java.util.List;
import java.util.Queue;

public class MessagesSender implements Runnable {

    private Queue<Message> queue;
    private List<Client> clients;

    public MessagesSender(Queue<Message> queue, List<Client> clients) {

        this.queue = queue;
        this.clients = clients;
    }
    @Override
    public void run() {
        while (true) {
            Message message = queue.poll();
            if(message == null) {
                continue;
            }
            SendMessage(message);
        }

        }

    private void SendMessage(Message message) {
        if (message.to == null) {
            sendAll(message,clients);
        } else {
            send(message);
        }
    }

    private void send(final Message message) {
        synchronized (clients) {
            for(Client client : clients) {
                if(client.getPrintWriter().equals(message.to)) {
                    client.getPrintWriter().println(message.text);
                    return;
                }
            }

        }

    }

    private void sendAll(Message message , List<Client> clients) {
        synchronized (clients) {
            for(Client client : clients) {
                client.getPrintWriter().println(message.text);
            }
        }
    }
}
        
    

