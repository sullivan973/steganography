import { Component, OnInit, Input } from '@angular/core';
import { ThreadData } from '../thread-modal/thread-data';
import { Post } from './post';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {
  @Input() postData : Post;
  constructor() { }

  ngOnInit() {
  }

}
