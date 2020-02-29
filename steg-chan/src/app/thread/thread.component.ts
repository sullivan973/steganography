import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-thread',
  templateUrl: './thread.component.html',
  styleUrls: ['./thread.component.scss']
})

export class ThreadComponent implements OnInit {  
  @Input() title: string;
  constructor() { }

  ngOnInit() {
  }

}
