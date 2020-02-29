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
}
