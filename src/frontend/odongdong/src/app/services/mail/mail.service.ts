import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { CommonService } from "../common/common.service";

const axios = require('axios');

@Injectable({
    providedIn: 'root',
})
export class MailService {
    constructor(
        public commonService: CommonService,
    ) {}

    async sendMail(content) {
        if(!(await this.commonService.checkNetworkStatus())) return;
        try {
            const response = await axios({
                method: 'post',
                url: `${environment.apiUrl}/api/mail/send`,
                data: content,
                responseType: 'json',
            });
            return response;
        } catch(error) {
            return error.response;
        }
    }

}