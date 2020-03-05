import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ThreadDetailComponent } from './thread-detail/thread-detail.component';
import { TitlePageComponent } from './title-page/title-page.component';

const routes: Routes = [
  { path: 'thread-detail/:id', component: ThreadDetailComponent },
  { path: '**', redirectTo:'', component: TitlePageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
