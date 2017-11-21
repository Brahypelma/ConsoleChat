import java.io.IOException;
import java.util.List;
import java.util.Queue;

public class IncomingMessageProcessor {
private Client client;
    private List<Client> clientList;
    private Queue<Message> messagesQueue;

    IncomingMessageProcessor(final Client client,List<Client> clientList ,Queue<Message> messagesQueue ) {
        this.client = client;
        this.clientList = clientList;
        this.messagesQueue = messagesQueue;
    }
    public void process() throws IOException {
        while (true) {

            String clientMessage = client.getReader().readLine();
            if(clientMessage == null || clientMessage.equals("!exit")) {
                clientList.remove(client);
                messagesQueue.add(new Message(client.getSocket().getInetAddress() +  " has left the channel"));
                return;
            }
            messagesQueue.add(new Message(client.getSocket().getInetAddress() + " > " + clientMessage));
        }
    }
    }
