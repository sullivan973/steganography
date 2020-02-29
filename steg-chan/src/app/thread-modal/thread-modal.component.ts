import { Component, OnInit, ElementRef, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-thread-modal',
  templateUrl: './thread-modal.component.html',
  styleUrls: ['./thread-modal.component.scss']
})
export class ThreadModalComponent implements OnInit {
  @Output() hideModalEvent: EventEmitter<boolean> = new EventEmitter();
  constructor(private element: ElementRef) { }

  ngOnInit() {
  }

  hideModal() {
    this.hideModalEvent.emit(false);
  }
}