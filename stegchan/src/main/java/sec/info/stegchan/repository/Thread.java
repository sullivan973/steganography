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
  private List<Post> postList;

  public Thread() {}

  public Thread(String title, List<Post> postList) {
    this.title = title;
    this.postList = postList;
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

  public List<Post> getPostList() {
    return postList;
  }

  public void setPostList(List<Post> postList) {
    this.postList = postList;
  }
}
