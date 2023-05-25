import { Injectable } from '@angular/core';
import { customAxios } from '../utils/axios';
import { CommonService } from './commonService';
import { AuthResponse } from '../types/response';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private common: CommonService) {}

  async createUUID(): Promise<AuthResponse> {
    if (!this.common.checkNetworkStatus()) {
      throw new Error('네트워크가 연결되지 않았습니다.');
    }

    try {
      const response = await customAxios.get('api/member/register');

      return response;
    } catch (error) {
      throw new Error('id를 생성하지 못했습니다.');
    }
  }
}
