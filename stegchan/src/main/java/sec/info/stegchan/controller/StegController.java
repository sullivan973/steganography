package sec.info.stegchan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import sec.info.stegchan.model.NewPostData;
import sec.info.stegchan.model.NewThreadData;
import sec.info.stegchan.model.ThreadDetail;
import sec.info.stegchan.model.Thumbnail;
import sec.info.stegchan.repository.Post;
import sec.info.stegchan.repository.PostRepository;
import sec.info.stegchan.repository.Thread;
import sec.info.stegchan.repository.ThreadRepository;
import sec.info.stegchan.service.PasswordService;
import sec.info.stegchan.service.Steganography;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@EnableWebMvc
@CrossOrigin
public class StegController {
  @Autowired
  private ThreadRepository threadRepository;
  @Autowired
  private PostRepository postRepository;
  private final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,50}$";

  @GetMapping("/threads")
  public ResponseEntity<List<Thumbnail>> getThreads() {
    List<Thumbnail> thumbnails = new ArrayList<>();
    Iterable<Thread> threadIterator = threadRepository.findAll();
    for (Thread thread : threadIterator) {
      Post post = thread.getPostList().get(0);
      thumbnails.add(
              new Thumbnail(thread.getTitle(), Post.postToBase64URL(post), thread.getThreadId()
      ));
    }
    return ResponseEntity.ok(thumbnails);
  }

  @PostMapping("/create/thread")
  //newThreadData image comes in as a base64URI string, data:image/<type>;base64,<data>
  public ResponseEntity createThread(@RequestBody NewThreadData newThreadData) {
    //Test password syntax
    if(newThreadData.getPassword() == null || !newThreadData.getPassword().matches(PASSWORD_REGEX)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password syntax invalid");
    }
    //check if the image looks like it should/exists
    if(newThreadData.getImageBase64DataUrl() == null || !newThreadData.getImageBase64DataUrl().matches("data:image.*")) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing or invalid image file");
    }
    if(newThreadData.getMessage() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing message text");
    }
    if(newThreadData.getTitle() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing title text");
    }

    String[] base64Data = newThreadData.getImageBase64DataUrl().split(",");
    String imageType = base64Data[0].substring(base64Data[0].indexOf('/') + 1, base64Data[0].indexOf(';'));
    try {
      byte[] encodedImage = Steganography.encodeBase64(base64Data[1], newThreadData.getMessage(), imageType);

      //add new thread to database with encoded image
      Post post = new Post(encodedImage, imageType);
      List<Post> postList = new ArrayList<>();
      postList.add(post);

      String salt = PasswordService.generateSalt();
      String hashedPassword = PasswordService.generateHash(salt + newThreadData.getPassword());
      Thread thread = new Thread(newThreadData.getTitle(), postList, hashedPassword, salt);
      post.setThread(thread);

      //this should cascade and create the post as well
      threadRepository.save(thread);
      return ResponseEntity.status(HttpStatus.OK).body(null);
    } catch (IOException | NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Thread, Try again");
  }

  @PostMapping("/create/post")
  public ResponseEntity createPost(@RequestBody NewPostData newPostData) {
    //check if the image looks like it should/exists
    if(newPostData.getImage() == null || !newPostData.getImage().matches("data:image.*")) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing or invalid image file");
    }
    //check if message is null
    if(newPostData.getMessage() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing message");
    }
    String[] base64Data = newPostData.getImage().split(",");
    String imageType = base64Data[0].substring(base64Data[0].indexOf('/') + 1, base64Data[0].indexOf(';'));
    try {
      byte[] encodedImage = Steganography.encodeBase64(base64Data[1], newPostData.getMessage(), imageType);

      //create new encoded post
      Post post = new Post(encodedImage, imageType);
      Optional<Thread> threadOptional = threadRepository.findById(newPostData.getThreadId());
      //if thread doesn't exist
      if(!threadOptional.isPresent()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thread was not found in database");
      }
      post.setThread(threadOptional.get());

      //this should cascade and add the post to the thread as well
      postRepository.save(post);
      return ResponseEntity.status(HttpStatus.OK).body(null);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to commit Post, Try again");
  }

  //Returns the list of posts for thread with threadId = id
  //includes post id, the title for the first post, and the encoded image
  @GetMapping("/thread/{id}")
  public ResponseEntity getThreadDetails(@PathVariable("id") int id) {
    Optional<Thread> threadOptional = threadRepository.findById(id);
    //if found in database
    if(threadOptional.isPresent()) {
      //pack the posts into a list of images with post id
      List<ThreadDetail> threadDetails = new ArrayList<>();
      List<Post> postList = threadOptional.get().getPostList();
      for (Post post: postList) {
        threadDetails.add(new ThreadDetail(null, Post.postToBase64URL(post), post.getPostId(), null));
      }
      //add title into the first post for header post
      threadDetails.get(0).setTitle(threadOptional.get().getTitle());
      return ResponseEntity.status(HttpStatus.OK).body(threadDetails);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Thread with id: " + id + " was not found.");
  }

  @PostMapping("/thread/decode/{id}")
  private ResponseEntity getDecodedThread(@PathVariable("id") int id, @RequestBody String password) {
    //Test password syntax
    if(password == null || !password.matches(PASSWORD_REGEX)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password syntax invalid");
    }

    Optional<Thread> threadOptional = threadRepository.findById(id);

    if(threadOptional.isPresent()) {
      //Check password
      String hashedPassword = null;
      try {
        String saltedPassword = threadOptional.get().getSalt() + password;
        hashedPassword = PasswordService.generateHash(saltedPassword);
        if(!hashedPassword.equals(threadOptional.get().getPassword())) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password, try again");
        }
      } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
      }

      //pack the posts into a list of images with post id
      List<ThreadDetail> threadDetails = new ArrayList<>();
      List<Post> postList = threadOptional.get().getPostList();
      for (Post post: postList) {
        //extract the message
        String message = null;
        try {
          message = Steganography.decodeFromBinaries(post.getStegImage());
        } catch (IOException e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        threadDetails.add(new ThreadDetail(null, Post.postToBase64URL(post), post.getPostId(), message));
      }
      //add title into the first post for header post
      threadDetails.get(0).setTitle(threadOptional.get().getTitle());
      return ResponseEntity.status(HttpStatus.OK).body(threadDetails);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Thread with id: " + id + " was not found.");
  }
}
