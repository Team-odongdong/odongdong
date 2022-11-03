import { Injectable } from "@angular/core";
import axios from "axios";
import { environment } from "src/environments/environment";
import { CommonService } from "../common/common-service";

@Injectable({
    providedIn: 'root',
})
export class BathroomService {
    constructor(
        public commonService: CommonService,
    ) {}

    async get1kmBathroomList(lat: number, lng: number) {
        try {
            const response = await axios({
                method: 'get',
                url: `${environment.apiUrl}/api/bathroom/list?latitude=${lat}&longitude=${lng}`,
                responseType: 'json',
            });
            return response;
        } catch(error) {
            return error.response;
        }
    }

    async addBathroom(data, images?) {
        if(!(await this.commonService.checkNetworkStatus())) return;

        try {
            const formData = new FormData();
            formData.append('latitude', data.latitude);
            formData.append('longitude', data.longitude);
            formData.append('title', data.title);
            formData.append('isLocked', data.isLocked);
            formData.append('address', data.address);
            formData.append('addressDetail', data.addressDetail);
            formData.append('rate', data.rate);
            formData.append('isUnisex', data.isUnisex);
            if(images) {
                images.forEach((image) => {
                    formData.append('images', image.fileBlob, image.fileName);
                });
            }

            const response = await axios({
                method: 'post',
                url: `${environment.apiUrl}/api/bathroom/add`,
                data: formData,
                responseType: 'json',
            });
            return response;
        } catch(error) {
            return error.reponse;
        }
    }

    async getAddressName(lat: number, lng: number) {
        try {
            const response = await axios({
                method: 'get',
                url: `${environment.apiUrl}/api/bathroom/address?latitude=${lat}&longitude=${lng}`,
                responseType: 'json',
            });
            return response;
        } catch(error) {
            return error.response;
        }
    }

}