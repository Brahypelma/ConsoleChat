import java.util.List;
import java.util.Queue;

public class IncomingMessageProcessorCreator {
    public IncomingMessageProcessor create(Client client , List<Client> clientList , Queue<Message> messagesQueue) {
        return new IncomingMessageProcessor(client,clientList,messagesQueue);
    }
}
