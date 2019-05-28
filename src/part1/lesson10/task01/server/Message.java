package part1.lesson10.task01.server;

public class Message {
  private String from;
  private String to;
  private final String message;

  public Message(String message) {
    this(null, null, message);
  }

  public Message(String from, String to, String message) {
    this.from = from;
    this.to = to;
    this.message = message;
  }

  public Message from(String from) {
    this.from = from;
    return this;
  }

  public Message to() {
    this.to = to;
    return this;
  }

  public String message() {
    return message;
  }

  public String sender() {
    return from;
  }

  public String recipient() {
    return to;
  }
}
