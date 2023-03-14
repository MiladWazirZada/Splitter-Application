package propra.splitter.application.exceptions;

public class NotPossibleException extends RuntimeException {

  private final long id;
  private final String user;

  public NotPossibleException(long id, String user) {
    this.id = id;
    this.user = user;
  }

  public long getId() {
    return id;
  }

  public String getUser() {
    return user;
  }
}
