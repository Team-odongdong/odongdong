import { AxiosError } from 'axios';

export interface ApiResponse {
  status: number;
  data: {
    code: number;
    result: object | [];
  };
}

export interface ApiResponseError extends ApiResponse {
  response: object;
}
