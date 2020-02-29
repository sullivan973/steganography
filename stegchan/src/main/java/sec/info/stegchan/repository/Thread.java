package sec.info.stegchan.repository;

import javax.persistence.*;
import java.util.List;

@Entity
public class Thread {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer threadId;

  private String title;

  @OneToMany(mappedBy = "thread")
  private List<Post> originalPost;
}
