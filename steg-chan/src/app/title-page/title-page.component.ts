import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/api-service/api-service.service';

@Component({
  selector: 'title-page',
  templateUrl: './title-page.component.html',
  styleUrls: ['./title-page.component.scss']
})
//Author: Thomas Sullivan
export class TitlePageComponent implements OnInit {
  showThreadModal: boolean;
  constructor(private apiService: ApiService) {
    this.showThreadModal = false;
  }
  
  threads = [];
  ngOnInit() {
    this.reloadThreads();
  }

  reloadThreads() {
    this.apiService.getAllThreads().subscribe(
      response => {
        this.threads = response;
      },
      err => {
        console.log(err);
      }
    );
  }

  showNewThreadModal(value: boolean) {
    this.showThreadModal = value;
  }
}
