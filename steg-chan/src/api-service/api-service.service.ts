import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ThreadModal } from 'src/app/thread-modal/thread-modal';
import { Thread } from 'src/app/thread/thread';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  getAllThreads(): Observable<Thread[]> {
    return this.http.get<Thread[]>('http://localhost:8080/threads');
  }

  createNewThread(formData: ThreadModal): Observable<any> {
    return this.http.post('http://localhost:8080/create/thread', formData)
  }
}
