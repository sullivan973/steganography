package sec.info.stegchan.model;

public class ThreadDetail {
  private String title;
  private String image;
  private int postId;

  public ThreadDetail(String title, String image, int postId) {
    this.title = title;
    this.image = image;
    this.postId = postId;
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
}
