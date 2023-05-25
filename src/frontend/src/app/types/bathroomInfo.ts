import { LatLng } from './location';

export interface BathroomInfo extends LatLng {
  title: string;
  isLocked: string;
  address: string;
  addressDetail: string;
  imageUrl: string;
  rate: number;
  isUnisex: boolean;
}

export interface BathroomDetailInfo extends BathroomInfo {
  id: number;
  isOpened: string;
  operationTime: string;
  distance: number;
  bathroomId?: number;
}
