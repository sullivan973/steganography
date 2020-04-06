import { Component, OnInit, Input } from '@angular/core';
import { Thread } from './thread';

@Component({
  selector: 'app-thread',
  templateUrl: './thread.component.html',
  styleUrls: ['./thread.component.scss']
})
//Author: Thomas Sullivan
export class ThreadComponent implements OnInit {  
  @Input() threadData: Thread;
  constructor() { }

  ngOnInit() {
  }
}
