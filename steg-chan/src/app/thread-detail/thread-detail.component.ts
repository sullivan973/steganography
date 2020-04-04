import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from 'src/api-service/api-service.service';
import { Post } from 'src/app/post/post';

@Component({
  selector: 'app-thread-detail',
  templateUrl: './thread-detail.component.html',
  styleUrls: ['./thread-detail.component.scss']
})
export class ThreadDetailComponent implements OnInit {
  postList: Post[] = [];
  threadId: string;
  showPostModal: boolean;
  showDecodeModal: boolean;
  showAlert: boolean;
  alertText: string;
  constructor(
    private route: ActivatedRoute,
    private apiService: ApiService
  ) { 
    //dummy original post for loading, + operator is cast to number
    this.postList[0] = new Post('', null, +this.threadId, null, "Loading Thread...");
  }

  ngOnInit() {
    this.reloadPosts();
  }

  reloadPosts() {
    this.showPostModal = false;
    this.showDecodeModal = false;
    this.showAlert = false;
    this.threadId = this.route.snapshot.paramMap.get('id');
    this.apiService.getThread(this.threadId).subscribe(
      data => {
        this.postList = data;
      },
      error => {
        console.log(error);
      }
    );
  }

  renderDecoded(password: string) {
    this.apiService.getDecodedThread(this.threadId, password).subscribe(
      data => {
        this.postList = data;
      },
      error => {
        //body of error shown to the user
        this.alertText = error.error;
        this.showAlert = true;
        setTimeout(() => {this.showAlert = false}, 3000);
      }
    )
  }

  showNewPostModal(value: boolean) {
    this.showPostModal = value;
  }

  displayDecodeModal(value: boolean) {
    this.showDecodeModal = value;
  }
}
