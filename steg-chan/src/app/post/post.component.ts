import { Component, OnInit, Input } from '@angular/core';
import { ThreadData } from '../thread-modal/thread-data';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {
  @Input() postData : ThreadData;
  constructor() { }

  ngOnInit() {
  }

}
