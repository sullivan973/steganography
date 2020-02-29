package sec.info.stegchan.model;

import java.sql.Blob;

public class Thumbnail {
  private String title;
  private byte[] image;

  public Thumbnail(String title, byte[] stegImage) {
    this.title = title;
    this.image = stegImage;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }
}
