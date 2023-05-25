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
  status: number;
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

export interface ProfileResponse {
  status: number;
  data: {
    code: number;
    isSuccess: boolean;
    message: string;
    result: {
      allRegisterBathroomNum: number;
      name: string;
      notRegisterBathroomNum: 0;
      notRegisterBathroomTitles: string[];
      registerBathroomNum: 0;
      registerBathroomTitles: string[];
      uuid: string;
    };
  };
}
