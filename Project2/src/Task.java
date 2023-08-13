import java.time.LocalDateTime;

public interface Task {

  String getDescription();

  int getCompletionStatus();

  boolean isDone();

  void markAsDone();

  LocalDateTime getTime();
}