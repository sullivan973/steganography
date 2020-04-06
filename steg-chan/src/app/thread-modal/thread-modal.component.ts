import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ThreadData } from './thread-data';
import { ApiService } from 'src/api-service/api-service.service';

@Component({
  selector: 'app-thread-modal',
  templateUrl: './thread-modal.component.html',
  styleUrls: ['./thread-modal.component.scss']
})
// Author: Thomas Sullivan
export class ThreadModalComponent implements OnInit {
  @Output() hideModalEvent: EventEmitter<boolean> = new EventEmitter();
  @Output() reloadThreadsEvent: EventEmitter<boolean> = new EventEmitter();
  threadModal: ThreadData;
  showAlert: boolean;
  alertText: string;
  constructor(private apiService: ApiService) { }

  ngOnInit() {
    this.threadModal = new ThreadData();
    this.showAlert = false;
  }

  hideModal() {
    this.hideModalEvent.emit(false);
  }

  fileChange(event) {
    var reader = new FileReader()
    reader.readAsDataURL(event.target.files[0]);
    reader.onloadend = () => {
      this.threadModal.imageBase64DataUrl = reader.result as string;
    };
  }

  onSubmit() {
    this.apiService.createNewThread(this.threadModal).subscribe(
      () => {
        this.reloadThreadsEvent.emit(true);
        this.hideModalEvent.emit(false);
      },
      error => {
        this.alertText = error.error;
        this.showAlert = true;
        setTimeout(() => {this.showAlert = false}, 3000);
      }
    );
  }
}