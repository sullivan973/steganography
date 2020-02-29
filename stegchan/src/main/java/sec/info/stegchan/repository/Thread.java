package sec.info.stegchan.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Thread {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer threadId;

  private String title;

  //private Post originalPost;
}
