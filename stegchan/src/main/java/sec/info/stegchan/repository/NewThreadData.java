package sec.info.stegchan.repository;

public class NewThreadData {
  private String title;
  private String message;
  private String imageBase64DataUrl;

  public NewThreadData() {}
  public NewThreadData(String title, String message, String imageBase64DataUrl) {
    this.title = title;
    this.message = message;
    this.imageBase64DataUrl = imageBase64DataUrl;
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

  public String getImageBase64DataUrl() {
    return imageBase64DataUrl;
  }

  public void setImageBase64DataUrl(String imageBase64DataUrl) {
    this.imageBase64DataUrl = imageBase64DataUrl;
  }
}
