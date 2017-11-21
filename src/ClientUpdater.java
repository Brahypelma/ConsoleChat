import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientUpdater implements Runnable {

    private final Socket clientSocket;
    private final Queue<Message> messagesQueue;
    private List<Client> clientList;
    private ClientCreator clientCreator;
    private IncomingMessageProcessorCreator incomingMessageProcessorCreator;

    ClientUpdater(Socket clientSocket,
                  ConcurrentLinkedQueue<Message> messagesQueue,
                  List<Client> userList,
                  ClientCreator clientCreator,
                  IncomingMessageProcessorCreator incomingMessageProcessorCreator) {

        this.clientSocket = clientSocket;
        this.messagesQueue = messagesQueue;
        this.clientList = userList;
        this.clientCreator = clientCreator;
        this.incomingMessageProcessorCreator = incomingMessageProcessorCreator;
    }

    @Override
    public void run() {
        try {
            processClient();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (!clientSocket.isClosed()) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void processClient() throws IOException {
        Client client = clientCreator.createClient(clientSocket);
        clientList.add(client);
        messagesQueue.add(new Message("Welcome " + clientSocket.getInetAddress() + "!!!", client));
        IncomingMessageProcessor incomingMessageProcessor = incomingMessageProcessorCreator.create(client,clientList,messagesQueue);
        incomingMessageProcessor.process();


    }
}

