import { Injectable } from '@angular/core';
import axios from "axios";
import {jwtDecode} from "jwt-decode";
import {User} from "../domain/user";
@Injectable({
  providedIn: 'root'
})
export class AxiosService {

  constructor() {
    axios.defaults.url="http://localhost:8080/app";
    axios.defaults.headers.post["Content-Type"] = "application/json";
  }

  getAuthToken(): string | null{
    return window.localStorage.getItem('auth_token');
  }

  setAuthToken(token: string | null){
    if(token !== null){
      window.localStorage.setItem("auth_token",token);

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

  request(method:string,url:string,data: any): Promise<any>{
    let headers={};

    if(this.getAuthToken() !== null){
      headers = {"Authorization":"Bearer " + this.getAuthToken()};
    }

    return axios({
      method : method,
      url: url,
      data: data,
      headers:headers
    });
  }
}
