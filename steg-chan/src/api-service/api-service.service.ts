import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Post } from 'src/app/post/post';
import { Thread } from 'src/app/thread/thread';
import { ThreadData } from 'src/app/thread-modal/thread-data';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  getAllThreads(): Observable<Thread[]> {
    return this.http.get<Thread[]>('http://localhost:8080/threads');
  }

  createNewThread(formData: ThreadData): Observable<any> {
    return this.http.post('http://localhost:8080/create/thread', formData)
  }

  getThread(id : string) : Observable<Post[]> {
    return this.http.get<Post[]>(`http://localhost:8080/thread/${id}`);
  }
}
