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
            const bathroomRequestDto = {
                "latitude": data.latitude,
                "longitude": data.longitude,
                "title": data.title,
                "isLocked": data.isLocked,
                "address": data.address,
                "addressDetail": data.addressDetail,
                "imageUrl": "",
                "rate": data.rate,
                "isUnisex": data.isUnisex,
            };

            const formData = new FormData();

            const json = JSON.stringify(bathroomRequestDto);
            const blob = new Blob([json], { type: "application/json" });
            formData.append("bathroomRequestDto", blob);
            
            if(images.length) {
                formData.append("bathroomImg", images[0].fileBlob, images[0].fileName);
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