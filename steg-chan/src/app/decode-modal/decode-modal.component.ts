import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ApiService } from 'src/api-service/api-service.service';

@Component({
  selector: 'app-decode-modal',
  templateUrl: './decode-modal.component.html',
  styleUrls: ['./decode-modal.component.scss']
})
export class DecodeModalComponent implements OnInit {

  @Output() hideModalEvent: EventEmitter<boolean> = new EventEmitter();
  @Output() decodePostsEvent: EventEmitter<string> = new EventEmitter();
  password: string;
  constructor(private apiService: ApiService) { }

  ngOnInit() {
  }

  hideModal() {
    this.hideModalEvent.emit(false);
  }

  onSubmit() {
    this.decodePostsEvent.emit(this.password);
    this.hideModalEvent.emit(false);
  }
}
