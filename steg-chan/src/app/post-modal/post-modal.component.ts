import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { Post } from '../post/post';
import { ApiService } from 'src/api-service/api-service.service';

@Component({
  selector: 'app-post-modal',
  templateUrl: './post-modal.component.html',
  styleUrls: ['./post-modal.component.scss']
})
//Author: Thomas Sullivan
export class PostModalComponent implements OnInit {
  @Input() threadId: string;
  @Output() hideModalEvent: EventEmitter<boolean> = new EventEmitter();
  @Output() reloadPostsEvent: EventEmitter<boolean> = new EventEmitter();
  postData: Post;
  showAlert: boolean;
  alertText: string;
  constructor(private apiService: ApiService) { }

  ngOnInit() {
    this.postData = new Post();
    this.showAlert = false;
  }

  hideModal() {
    this.hideModalEvent.emit(false);
  }

  fileChange(event) {
    var reader = new FileReader()
    reader.readAsDataURL(event.target.files[0]);
    reader.onloadend = () => {
      this.postData.image = reader.result as string;
    };
  }

  onSubmit() {
    this.postData.threadId = parseInt(this.threadId);
    if(!isNaN(this.postData.threadId)) {
      this.apiService.createNewPost(this.postData).subscribe(
        () => {
          this.reloadPostsEvent.emit(true);
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
}
