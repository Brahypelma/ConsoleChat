import java.io.*;
import java.net.Socket;

public class ClientCreator {

     public Client createClient(Socket socket) throws IOException {
         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
         PrintWriter clientWriter = new PrintWriter(writer, true);

         BufferedReader clientReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
         return new Client(socket, clientWriter, clientReader);
     }
 }
