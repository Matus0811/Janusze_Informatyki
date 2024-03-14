import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';
import { Observable } from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private url : string = environment.url;

  constructor(private http: HttpClient) {}

  getUser(id: number) : Observable<User>{
    return this.http.get<User>(`${this.url}/users/${id}`);
  }
}
