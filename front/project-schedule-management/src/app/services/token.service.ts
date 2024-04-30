import { Injectable } from '@angular/core';
import axios from "axios";
import {jwtDecode} from "jwt-decode";
import {User} from "../domain/user";

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  authenticated = false;

  getAuthToken(): string | null{
    return window.localStorage.getItem('auth_token');
  }

  setAuthToken(token: string | null){
    if(token !== null){
      window.localStorage.setItem("auth_token",token);
      this.authenticated = true;
      let decodedToken : any = jwtDecode(token);

      let loggedUser:User = {
        username: decodedToken.iss,
        name: decodedToken.name,
        surname: decodedToken.surname,
        email: decodedToken.email,
        authorities: decodedToken.authorities
      };
      window.localStorage.setItem("logged_user",JSON.stringify(loggedUser));
    }else{
      window.localStorage.removeItem("auth_token");
      window.localStorage.removeItem("logged_user");
    }
  }

  removeAuthToken() {
    localStorage.clear();
    this.authenticated = false;
    return localStorage.length == 0;
  }
}
