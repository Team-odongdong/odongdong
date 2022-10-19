import { Injectable } from "@angular/core";
import axios from "axios";
import { environment } from "src/environments/environment";
import { CommonService } from "../common/common.service";

@Injectable({
    providedIn: 'root',
})
export class BathroomService {
    constructor(
        public commonService: CommonService,
    ) {}

    async get1kmBathroomList(lat, lng) {
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

    async addBathroom(data) {
        if(!(await this.commonService.checkNetworkStatus())) return;
        try {
            const response = await axios({
                method: 'post',
                url: `${environment.apiUrl}/api/bathroom/add`,
                data,
                responseType: 'json',
            });
            return response;
        } catch(error) {
            return error.reponse;
        }
    }

    async getAddressName(lat, lng) {
        try {
            const response = await axios({
                method: 'get',
                url: `${environment.apiUrl}/api/getBathroomInfo?latitude=${lat}&longitude=${lng}`,
                responseType: 'json',
            });
            return response;
        } catch(error) {
            return error.response;
        }
    }

}