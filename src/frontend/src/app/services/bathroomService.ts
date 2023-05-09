import { Injectable } from '@angular/core';
import { CommonService } from './commonService';
import { customAxios } from '../utils/axios';
import { LatLng } from '../types/location';
import { BathroomInfo } from '../types/bathroomInfo';
import { createFormData } from '../utils/bathroomData';
import { ApiResponse } from '../types/response';

@Injectable({
  providedIn: 'root',
})
export class BathroomService {
  constructor(private commonService: CommonService) {}

  async get1kmBathroomList({ lat, lng }: LatLng): Promise<ApiResponse> {
    try {
      const response = await customAxios.get(
        `api/bathroom/list?latitude=${lat}&longitude=${lng}&distance=1`
      );
      return response;
    } catch (error) {
      throw new Error('에러 발생');
    }
  }

  async addBathroom(data: BathroomInfo, images: any[]): Promise<ApiResponse> {
    if (!this.commonService.checkNetworkStatus()) {
      throw new Error('네트워크가 연결되지 않았습니다.');
    }

    const formData = createFormData(data, images);
    try {
      const response = await customAxios.post('api/bathroom/add', formData);
      return response;
    } catch (error) {
      throw new Error('에러 발생');
    }
  }

  async editBathroom(data: BathroomInfo): Promise<ApiResponse> {
    try {
      const response = await customAxios.post('api/bathroom/edit', data);
      return response;
    } catch (error) {
      throw new Error('에러 발생');
    }
  }

  async getAddressName({ lat, lng }: LatLng): Promise<ApiResponse> {
    try {
      const response = await customAxios.get(
        `api/bathroom/address?latitude=${lat}&longitude=${lng}`
      );
      return response;
    } catch (error) {
      throw new Error('에러 발생');
    }
  }

  async registerRating(bathroomId: number, rate: number): Promise<ApiResponse> {
    try {
      const response = await customAxios.post('api/rating', {
        bathroomId,
        score: rate,
      });
      return response;
    } catch (error) {
      throw new Error('에러 발생');
    }
  }
}
