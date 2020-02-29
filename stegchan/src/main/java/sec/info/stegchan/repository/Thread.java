package sec.info.stegchan.repository;

import javax.persistence.*;
import java.util.List;

@Entity
public class Thread {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer threadId;

  private String title;

  @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL)
  private List<Post> originalPost;

  public Thread() {}

  public Thread(String title, List<Post> originalPost) {
    this.title = title;
    this.originalPost = originalPost;
  }

  public Integer getThreadId() {
    return threadId;
  }

  public void setThreadId(Integer threadId) {
    this.threadId = threadId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<Post> getOriginalPost() {
    return originalPost;
  }

  public void setOriginalPost(List<Post> originalPost) {
    this.originalPost = originalPost;
  }
}
