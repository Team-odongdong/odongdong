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
                url: `${environment.apiUrl}/api/bathroom/list?latitude=${lat}&longitude=${lng}&distance=1`,
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

            // bathroomRequestDto.append('latitude', data.latitude);
            // bathroomRequestDto.append('longitude', data.longitude);
            // bathroomRequestDto.append('title', data.title);
            // bathroomRequestDto.append('isLocked', data.isLocked);
            // bathroomRequestDto.append('address', data.address);
            // bathroomRequestDto.append('addressDetail', data.addressDetail);
            // bathroomRequestDto.append('rate', data.rate);
            // bathroomRequestDto.append('isUnisex', data.isUnisex);
            // if(images.length) {
            //     images.forEach((image) => {
            //         bathroomRequestDto.append('bathroomImg', image.fileBlob, image.fileName);
            //     });
            //     // const json = JSON.stringify(images.fileBlob);
            //     // const blob = new Blob([json], { type: "application/json" });
            // }
            
            const json = JSON.stringify(data);
            const blob = new Blob([json], { type: "application/json" });
            formData.append("dto", blob);

            const response = await axios({
                method: 'post',
                url: `${environment.apiUrl}/api/bathroom/add`,
                // headers: { 'Content-Type': 'multipart/form-data' },
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