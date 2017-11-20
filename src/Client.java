import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader reader;

    public Client(Socket socket,PrintWriter printWriter , BufferedReader reader) {

        this.socket = socket;
        this.printWriter = printWriter;
        this.reader = reader;
    }

    public Socket getSocket() {
        return socket;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public BufferedReader getReader() {
        return reader;
    }
}
