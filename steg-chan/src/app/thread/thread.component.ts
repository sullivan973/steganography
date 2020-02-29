import { Component, OnInit, Input } from '@angular/core';
import { Thread } from './thread';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-thread',
  templateUrl: './thread.component.html',
  styleUrls: ['./thread.component.scss']
})

export class ThreadComponent implements OnInit {  
  @Input() threadData: Thread;
  imagePath: any;
  constructor(private domSanitizer: DomSanitizer) { }

  ngOnInit() {
  //   var reader = new FileReader();
  //   reader.readAsDataURL(this.threadData.image); 
  //   reader.onloadend = () => {
  //       var base64data = reader.result;                
        
  //   }
  // }
    this.imagePath = this.domSanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + this.threadData.image);
  }
}
