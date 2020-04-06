package sec.info.stegchan.model;
//Author: Thomas Sullivan
public class ThreadDetail {
  private String title;
  private String image;
  private int postId;
  private String message;

  public ThreadDetail(String title, String image, int postId, String message) {
    this.title = title;
    this.image = image;
    this.postId = postId;
    this.message = message;
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

  public int getPostId() {
    return postId;
  }

  public void setPostId(int postId) {
    this.postId = postId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
