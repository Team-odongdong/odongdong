import axios from 'axios';
import { environment } from 'src/environments/environment';

export const customAxios = axios.create({
  baseURL: environment.apiUrl,
  responseType: 'json',
});
