package sec.info.stegchan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import sec.info.stegchan.model.Thumbnail;
import sec.info.stegchan.repository.Thread;
import sec.info.stegchan.repository.*;
import sec.info.stegchan.service.Steganography;

import java.awt.image.BufferedImage;
import java.io.*;
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
  public ResponseEntity<List<Thumbnail>> getThreads() throws IOException {
    List<Thumbnail> thumbnails = new ArrayList<>();
    Iterable<Thread> threadIterator = threadRepository.findAll();
    for (Thread thread : threadIterator) {
      Post post = thread.getPostList().get(0);
      thumbnails.add(
              new Thumbnail(thread.getTitle(),
              "data:image/" + post.getImageType() + ";base64," + Base64Utils.encodeToString(post.getStegImage()),
              thread.getThreadId()
      ));
      //for debug purposes
      BufferedImage image = Steganography.createBufferedImage(thread.getPostList().get(0).getStegImage());
      byte[] data = Steganography.extractRGBBytes(image);
      String message = Steganography.decodeMessage(data);
    }
    return ResponseEntity.ok(thumbnails);
  }

  @PostMapping("/create/thread")
  //newThreadData image comes in as a base64URI string, data:image/<type>;base64,<data>
  //TODO: extract mime type and save in database
  public ResponseEntity createThread(@RequestBody NewThreadData newThreadData) {
    String[] base64Data = newThreadData.getImageBase64DataUrl().split(",");
    String imageType = base64Data[0].substring(base64Data[0].indexOf('/') + 1, base64Data[0].indexOf(';'));
    //turn base64 data into binary
    byte[] imageBinary = Base64Utils.decodeFromString(base64Data[1]);
    try {
      //encode message into image and get encoded bytes
      BufferedImage image = Steganography.createBufferedImage(imageBinary);
      byte[] RGBBytes = Steganography.extractRGBBytes(image);
      Steganography.encodeMessage(RGBBytes, newThreadData.getMessage());
      byte[] encodedImage = Steganography.bufferedImageToBytes(image, imageType);

      //add new thread to database with encoded image
      //we are saving rgbBytes to the database to avoid compression destroying the message
      Post post = new Post(encodedImage, imageType);
      List<Post> postList = new ArrayList<>();
      postList.add(post);
      Thread thread = new Thread(newThreadData.getTitle(), postList);
      post.setThread(thread);

      //this should cascade and create the post as well
      threadRepository.save(thread);
      return ResponseEntity.status(HttpStatus.OK).body(null);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
  }
}
