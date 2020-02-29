package sec.info.stegchan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import sec.info.stegchan.repository.PostRepository;
import sec.info.stegchan.repository.ThreadRepository;

@RestController
@EnableWebMvc
public class StegController {
  @Autowired
  private ThreadRepository threadRepository;
  @Autowired
  private PostRepository postRepository;

  @GetMapping("/hello")
  public ResponseEntity getThreads() {
    return ResponseEntity.ok("Hello World!");
  }

}
