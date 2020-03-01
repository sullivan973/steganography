package sec.info.stegchan.model;

import java.sql.Blob;

public class Thumbnail {
  private String title;
  private String image;

  public Thumbnail(String title, String stegImage) {
    this.title = title;
    this.image = stegImage;
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
}
