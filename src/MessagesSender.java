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
            sendAll(message);
        } else {
            send(message,message.to);
        }
    }

    private void send(final Message message, Client client) {
        synchronized (clients) {
                if(clients.contains(client)) {
                    client.getPrintWriter().println(message.text);
                }
            }

        }



    private void sendAll(Message message) {
        synchronized (clients) {
            for(Client client : clients) {
                client.getPrintWriter().println(message.text);
            }
        }
    }
}
        
    

