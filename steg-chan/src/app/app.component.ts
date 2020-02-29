import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/api-service/api-service.service';
import { Thread } from './thread/thread';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  
  constructor(private apiService: ApiService){}
  
  threads = ['first', 'second'];
  ngOnInit() {
    var blob = new Blob();

    // this.apiService.getAllThreads().subscribe(
    //   reponse => {
    //     this.threads = reponse;
    //   },
    //   err => {
    //     console.log(err);
    //   }
    // );
  }
}
