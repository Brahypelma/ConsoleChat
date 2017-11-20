import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Queue;

public class ClientMessageReader implements Runnable {

    private final Socket clientSocket;
    private final Queue<String> messagesQueue;
    private List<PrintWriter> userList;

    public ClientMessageReader(Socket clientSocket, Queue<String> messagesQueue, List<PrintWriter> userList) {
        this.clientSocket = clientSocket;
        this.messagesQueue = messagesQueue;

        this.userList = userList;
    }

    @Override
    public void run() {
        try {
            processClient();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(!clientSocket.isClosed()) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    private  void processClient() throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        PrintWriter clientWriter = new PrintWriter(writer, true);

        BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
        Client client = new Client(new Socket("localhost",10000),new PrintWriter(clientWriter),clientReader);

        while (true) {

                String clientMessage = clientReader.readLine();
            if(clientMessage == null || clientMessage.equals("!exit")) {
                userList.remove(clientWriter);
                System.out.println(client.getInetAddress() + " > has left the channel" );
                return;
            }
                messagesQueue.add(client.getInetAddress() + " > " + clientMessage);
            }
        }
    }

