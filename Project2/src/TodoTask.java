public class TodoTask implements Task {

  private String description;
  private boolean done;

  public TodoTask(String description) {
    this.description = description;
    this.done = false;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public boolean isDone() {
    return done;
  }

  @Override
  public void markAsDone() {
    done = true;
  }
}
