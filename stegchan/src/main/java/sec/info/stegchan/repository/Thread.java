package sec.info.stegchan.repository;

import javax.persistence.*;
import java.util.List;

@Entity
//Author: Thomas Sullivan & Cassidy Murphy
public class Thread {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer threadId;

  private String title;

  @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL)
  private List<Post> postList;

  private String password;

  private String salt;

  public Thread() {}

  public Thread(String title, List<Post> postList, String password, String salt) {
    this.title = title;
    this.postList = postList;
    this.password = password;
    this.salt = salt;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSalt() { return salt; }

  public void setSalt(String salt) { this.salt = salt; }
}
