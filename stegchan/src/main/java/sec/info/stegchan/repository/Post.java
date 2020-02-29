package sec.info.stegchan.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer postId;

  @Lob
  private Byte[] stegImage;

  @ManyToOne(targetEntity = Thread.class)
  @JoinColumn(name="threadId")
  @JsonIgnore
  private Thread thread;

  public Post(Byte[] stegImage) {
    this.stegImage = stegImage;
  }

  public Integer getPostId() {
    return postId;
  }

  public void setPostId(Integer postId) {
    this.postId = postId;
  }

  public Byte[] getStegImage() {
    return stegImage;
  }

  public void setStegImage(Byte[] stegImage) {
    this.stegImage = stegImage;
  }

  public Thread getThread() {
    return thread;
  }

  public void setThread(Thread thread) {
    this.thread = thread;
  }
}
