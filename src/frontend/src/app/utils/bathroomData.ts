import { BathroomInfo } from '../types/bathroomInfo';

export const createFormData = (bathroomInfo: BathroomInfo, images: any[]) => {
  const formData = new FormData();

  const json = JSON.stringify(bathroomInfo);
  const blob = new Blob([json], { type: 'application/json' });

  formData.append('bathroomRequestDto', blob);

  if (images.length) {
    formData.append('bathroomImg', images[0].fileBlob, images[0].fileName);
  }

  return formData;
};
