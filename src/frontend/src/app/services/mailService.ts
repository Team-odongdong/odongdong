import { Injectable } from '@angular/core';
import { CommonService } from './commonService';
import { customAxios } from '../utils/axios';
import { ApiResponse, ApiResponseError } from '../types/response';
import { AxiosError } from 'axios';

@Injectable({
  providedIn: 'root',
})
export class MailService {
  constructor(public commonService: CommonService) {}

  async sendMail(content: string): Promise<ApiResponse> {
    if (!this.commonService.checkNetworkStatus()) {
      throw new Error('네트워크가 연결되지 않았습니다.');
    }

    try {
      const response = await customAxios.post('api/mail/send', content, {
        headers: { 'Content-Type': 'text/plain' },
      });
      return response;
    } catch (error) {
      throw new Error('에러 발생');
    }
  }
}
