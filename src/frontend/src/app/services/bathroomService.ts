import { Injectable } from '@angular/core';
import { CommonService } from './commonService';
import { customAxios } from '../utils/axios';
import { LatLng } from '../types/location';
import { BathroomInfo } from '../types/bathroomInfo';
import { createFormData } from '../utils/bathroomData';
import { ApiResponse } from '../types/response';
import { DistanceOptionValues } from '../types/distance';
import { StorageService } from './storageService';

interface GetBathroomProps extends LatLng {
  distance: DistanceOptionValues;
}

@Injectable({
  providedIn: 'root',
})
export class BathroomService {
  constructor(
    private commonService: CommonService,
    private storage: StorageService
  ) {}

  async getBathrooms({
    latitude,
    longitude,
    distance,
  }: GetBathroomProps): Promise<ApiResponse> {
    try {
      const response = await customAxios.get(
        `api/bathroom/list?latitude=${latitude}&longitude=${longitude}&distance=${distance}`
      );
      return response;
    } catch (error) {
      throw new Error('화장실 목록 조회 에러 발생');
    }
  }

  async addBathroom(data: BathroomInfo, images: any[]): Promise<ApiResponse> {
    if (!this.commonService.checkNetworkStatus()) {
      throw new Error('네트워크가 연결되지 않았습니다.');
    }

    const id = await this.storage.getStorage('UUID');

    const formData = createFormData(data, images);
    try {
      const response = await customAxios.post('api/bathroom/add', formData, {
        headers: {
          Authorization: id,
        },
      });
      return response;
    } catch (error) {
      throw new Error('화장실 추가 에러 발생');
    }
  }

  async editBathroom(data: BathroomInfo): Promise<ApiResponse> {
    try {
      const id = await this.storage.getStorage('UUID');

      const response = await customAxios.post('api/bathroom/edit', data, {
        headers: {
          Authorization: id,
        },
      });
      return response;
    } catch (error) {
      throw new Error('화장실 편집 에러 발생');
    }
  }

  async getAddressName({ latitude, longitude }: LatLng): Promise<ApiResponse> {
    try {
      const response = await customAxios.get(
        `api/bathroom/address?latitude=${latitude}&longitude=${longitude}`
      );
      return response;
    } catch (error) {
      throw new Error('도로명 주소 조희 에러 발생');
    }
  }

  async registerRating(bathroomId: number, rate: number): Promise<ApiResponse> {
    try {
      const id = await this.storage.getStorage('UUID');

      const response = await customAxios.post(
        'api/rating',
        {
          bathroomId,
          score: rate,
        },
        {
          headers: {
            Authorization: id,
          },
        }
      );
      return response;
    } catch (error) {
      throw new Error('별점 등록 에러 발생');
    }
  }
}
