package sec.info.stegchan.repository;

import javassist.bytecode.ByteArray;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer postId;

  private ByteArray stegImage;
}
