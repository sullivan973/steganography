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
  
  threads = ['first', 'second'];
  ngOnInit() {
    
    // this.apiService.getAllThreads().subscribe(
    //   reponse => {
    //     this.threads = reponse;
    //   },
    //   err => {
    //     console.log(err);
    //   }
    // );
  }

  showNewThreadModal(value: boolean) {
    this.showThreadModal = value;
  }
}
