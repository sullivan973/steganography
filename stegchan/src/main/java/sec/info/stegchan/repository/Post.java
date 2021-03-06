package sec.info.stegchan.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.Base64Utils;

import javax.persistence.*;

@Entity
//Author: Thomas Sullivan
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer postId;

  @Lob
  private byte[] stegImage;

  private String imageType;

  @ManyToOne(targetEntity = Thread.class)
  @JoinColumn(name="threadId")
  @JsonIgnore
  private Thread thread;

  public Post() {};

  public Post(byte[] stegImage, String imageType) {
    this.stegImage = stegImage;
    this.imageType = imageType;
  }

  public Integer getPostId() {
    return postId;
  }

  public void setPostId(Integer postId) {
    this.postId = postId;
  }

  public byte[] getStegImage() {
    return stegImage;
  }

  public void setStegImage(byte[] stegImage) {
    this.stegImage = stegImage;
  }

  public Thread getThread() {
    return thread;
  }

  public void setThread(Thread thread) {
    this.thread = thread;
  }

  public String getImageType() {
    return imageType;
  }

  public void setImageType(String imageType) {
    this.imageType = imageType;
  }

  public static String postToBase64URL(Post post) {
    return "data:image/" + post.getImageType() + ";base64," + Base64Utils.encodeToString(post.getStegImage());
  }
}
