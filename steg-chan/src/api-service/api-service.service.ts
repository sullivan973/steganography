import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Post } from 'src/app/post/post';
import { Thread } from 'src/app/thread/thread';
import { ThreadData } from 'src/app/thread-modal/thread-data';

@Injectable({
  providedIn: 'root'
})
//Author: Thomas Sullivan
export class ApiService {

  constructor(private http: HttpClient) { }

  //fetches and returns all threads in database
  getAllThreads(): Observable<Thread[]> {
    return this.http.get<Thread[]>('http://localhost:8080/threads');
  }

  //creates a new thread from the create thread form data
  createNewThread(formData: ThreadData): Observable<any> {
    return this.http.post('http://localhost:8080/create/thread', formData);
  }
  
  //creates new post from the create post form data
  createNewPost(formData: Post): Observable<any> {
    return this.http.post('http://localhost:8080/create/post', formData);
  }

  //gets a specific thread by id and returns all related posts, strings encoded
  getThread(id : string) : Observable<Post[]> {
    return this.http.get<Post[]>(`http://localhost:8080/thread/${id}`);
  }

  getDecodedThread(id: string, password: string) : Observable<Post[]> {
    return this.http.post<Post[]>(`http://localhost:8080/thread/decode/${id}`, password);
  }
}
