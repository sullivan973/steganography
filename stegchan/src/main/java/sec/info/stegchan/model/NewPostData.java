package sec.info.stegchan.model;
//Author: Thomas Sullivan
public class NewPostData {
  private String image;
  private int threadId;
  private String message;

  public NewPostData(){}

  public NewPostData(String image, int threadId, String message) {
    this.image = image;
    this.threadId = threadId;
    this.message = message;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public int getThreadId() {
    return threadId;
  }

  public void setThreadId(int threadId) {
    this.threadId = threadId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
