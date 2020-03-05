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
  postList : Post[] = [];
  constructor(
    private route: ActivatedRoute,
    private apiService: ApiService
  ) { }

  ngOnInit() {
    let id = this.route.snapshot.paramMap.get('id');
    this.apiService.getThread(id).subscribe(
      data => {
        this.postList = data;
      },
      error => {
        console.log(error);
      }
    );
  }

}
