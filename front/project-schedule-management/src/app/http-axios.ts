import axios from "axios";
import {environment} from "../environment/environment";

const instance = axios.create({
  baseURL: environment.url,
  // timeout: 5000,
  headers: {
    post: {
      "Content-Type" : "application/json"
    }
  }
});

export default instance;
