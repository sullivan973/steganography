package sec.info.stegchan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import sec.info.stegchan.model.Thumbnail;
import sec.info.stegchan.repository.Thread;
import sec.info.stegchan.repository.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
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
  public ResponseEntity<List<Thumbnail>> getThreads() {
    List<Thumbnail> thumbnails = new ArrayList<>();
    Iterable<Thread> threadIterator = threadRepository.findAll();
    for (Thread thread : threadIterator) {
      thumbnails.add(
              new Thumbnail(thread.getTitle(),
              new String(thread.getOriginalPost().get(0).getStegImage())
      ));
    }
    return ResponseEntity.ok(thumbnails);
  }

  @PostMapping("/create/thread")
  public ResponseEntity createThread(@RequestBody NewThreadData newThreadData) {
    Post post = new Post(newThreadData.getImageBinary().getBytes());
    List<Post> postList = new ArrayList<>();
    postList.add(post);

    Thread thread = new Thread(newThreadData.getTitle(), postList);
    post.setThread(thread);

    //this should cascade and create the post as well
    threadRepository.save(thread);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }

}
