import java.time.LocalDateTime;
import probe.Task;

public class TodoTask implements Task {

  private String description;
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
    // this.time = LocalDateTime.now();
    this.time = time;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public int getCompletionStatus() {
    return done ? 1 : 0; // Возвратить 1 для выполненного, 0 для не завершенного
  }

  @Override
  public boolean isDone() {
    return done;
  }
 /* @Override
  public void markAsDone() {
    if (!done) {
      done = true;
      time = LocalDateTime.now(); //Обновите время, когда оно помечено как выполненное
    }
  }

  */

 /* @Override
  public void markAsDone() {
    if (!done) {
      done = true;
    }
  }

  */

  @Override
  public void markAsDone() {
    done = true;
  }

  @Override
  public LocalDateTime getTime() {
    return time;
  }

}