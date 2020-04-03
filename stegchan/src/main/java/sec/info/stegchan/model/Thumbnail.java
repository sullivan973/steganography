package sec.info.stegchan.model;

public class Thumbnail {
  private String title;
  private String image;
  private int threadId;

  public Thumbnail(String title, String stegImage, int threadId) {
    this.title = title;
    this.image = stegImage;
    this.threadId = threadId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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
}
