package sec.info.stegchan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import sec.info.stegchan.repository.PostRepository;
import sec.info.stegchan.repository.ThreadRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@EnableWebMvc
@CrossOrigin
public class StegController {
  @Autowired
  private ThreadRepository threadRepository;
  @Autowired
  private PostRepository postRepository;

  @GetMapping("/threads")
  public ResponseEntity<List<String>> getThreads() {
    List<String> threads = new ArrayList<>();
    threads.add("first from server");
    threads.add("second from server");
    threads.add("last from server");

    return ResponseEntity.ok(threads);
  }

}
