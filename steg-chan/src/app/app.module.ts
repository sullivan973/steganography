import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ThreadComponent } from './thread/thread.component';
import { ThreadDetailComponent } from './thread-detail/thread-detail.component';
import { PostComponent } from './post/post.component';
import { PostModalComponent } from './post-modal/post-modal.component';
import { ApiService } from 'src/api-service/api-service.service';
import { HttpClientModule } from '@angular/common/http';
import { ThreadModalComponent } from './thread-modal/thread-modal.component';
import { FormsModule }   from '@angular/forms';
import { TitlePageComponent } from './title-page/title-page.component';
import { DecodeModalComponent } from './decode-modal/decode-modal.component';

//Author: Thomas Sullivan
@NgModule({
  declarations: [
    AppComponent,
    ThreadComponent,
    ThreadDetailComponent,
    PostComponent,
    PostModalComponent,
    ThreadModalComponent,
    TitlePageComponent,
    DecodeModalComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule,
  ],
  providers: [ApiService],
  bootstrap: [AppComponent]
})
export class AppModule { }
