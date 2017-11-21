

public class Message {
    String text;
    Client to;

    public Message(final String text) {
        this.text = text;
    }

    public Message(final String text, final Client to) {
        this.text = text;
        this.to = to;


    }
}
