import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/api-service/api-service.service';
import { Thread } from './thread/thread';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
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
