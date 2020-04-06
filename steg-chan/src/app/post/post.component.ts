import { Component, OnInit, Input } from '@angular/core';
import { Post } from './post';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
//Author: Thomas Sullivan
export class PostComponent implements OnInit {
  @Input() postData : Post;
  constructor() { }

  ngOnInit() {
  }

}
