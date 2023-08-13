import java.time.LocalDateTime;

public class TodoTask implements Task {

  private final String description;
  private boolean done;
  private LocalDateTime time;

  public TodoTask(String description) {
    this.description = description;
    this.done = false;
    this.time = LocalDateTime.now();
  }

  public TodoTask(String description, LocalDateTime time) {
    this.description = description;
    this.done = false;
    this.time = time;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public int getCompletionStatus() {
    return done ? 1 : 0;
  }

  @Override
  public boolean isDone() {
    return done;
  }

  @Override
  public void markAsDone() {
    if (!done) {
      done = true;
      if (time == null) {
        time = LocalDateTime.now();
      }
    }
  }

  @Override
  public LocalDateTime getTime() {
    return time;
  }
}