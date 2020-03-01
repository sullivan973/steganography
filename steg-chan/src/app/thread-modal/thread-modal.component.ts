import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ThreadModal } from './thread-modal';
import { ApiService } from 'src/api-service/api-service.service';

@Component({
  selector: 'app-thread-modal',
  templateUrl: './thread-modal.component.html',
  styleUrls: ['./thread-modal.component.scss']
})
export class ThreadModalComponent implements OnInit {
  @Output() hideModalEvent: EventEmitter<boolean> = new EventEmitter();
  threadModal: ThreadModal;
  constructor(private apiService: ApiService) { }

  ngOnInit() {
    this.threadModal = new ThreadModal();
  }

  hideModal() {
    this.hideModalEvent.emit(false);
  }

  fileChange(event) {
    var reader = new FileReader()
    reader.readAsDataURL(event.target.files[0]);
    reader.onloadend = () => {
      this.threadModal.imageBinary = reader.result as string;
      //remove the dataURL prefix since the mime type is in the data
      //this.threadModal.imageBinary = this.threadModal.imageBinary.substring(this.threadModal.imageBinary.search(',') + 1);
    };
  }

  onSubmit() {
    this.apiService.createNewThread(this.threadModal).subscribe();
  }
}