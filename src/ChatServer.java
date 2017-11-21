import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by mikle on 11/14/17.
 */
public class ChatServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10000);
        ConcurrentLinkedQueue<Message> messagesQueue = new ConcurrentLinkedQueue<>();
        List<Client> clients  = Collections.synchronizedList(new ArrayList<>());
        MessagesSender sender = new MessagesSender(messagesQueue, clients);
        Thread messageSender = new Thread(sender);
        messageSender.start();

        while (true) {
            Socket clientSocket = serverSocket.accept();

            Runnable clientReader = new ClientUpdater(clientSocket,
                    messagesQueue,
                    clients,
                    new ClientCreator(),
                    new IncomingMessageProcessorCreator());

            Thread clientThread = new Thread(clientReader);
            clientThread.start();
        }

    }
}

