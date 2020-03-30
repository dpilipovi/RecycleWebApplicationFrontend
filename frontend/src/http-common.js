import axios from "axios";
import {store} from './store/store'

export default axios.create({
  baseURL: "http://localhost:8090/api",
  headers: {
    "Content-type": "application/json",
    "Authorization": store.state.token
  }
});