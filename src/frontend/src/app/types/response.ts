export interface ApiResponse {
  status: number;
  data: {
    code: number;
    isSuccess: boolean;
    message: string;
    result: object | [];
  };
}

export interface ApiResponseError extends ApiResponse {
  response: object;
}

export interface AuthResponse {
  data: {
    code: number;
    status: number;
    isSuccess: boolean;
    message: string;
    result: {
      name: string;
      uuid: string;
    };
  };
}
