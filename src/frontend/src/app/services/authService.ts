import { Injectable } from '@angular/core';
import { customAxios } from '../utils/axios';
import { CommonService } from './commonService';
import { AuthResponse, ProfileResponse } from '../types/response';
import { StorageService } from './storageService';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private common: CommonService, private storage: StorageService) {}

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

  async getProfile(): Promise<ProfileResponse> {
    try {
      const id = await this.storage.getStorage('UUID');

      const response = await customAxios.get('api/member/profile', {
        headers: {
          Authorization: id,
        },
      });

      return response;
    } catch (error) {
      throw new Error('프로필을 가져오지 못했습니다.');
    }
  }
}
