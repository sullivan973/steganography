package sec.info.stegchan.repository;

public class NewThreadData {
  private String title;
  private String message;
  private String imageBinary;

  public NewThreadData() {}
  public NewThreadData(String title, String message, String imageBinary) {
    this.title = title;
    this.message = message;
    this.imageBinary = imageBinary;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getImageBinary() {
    return imageBinary;
  }

  public void setImageBinary(String imageBinary) {
    this.imageBinary = imageBinary;
  }
}
