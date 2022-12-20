import { Injectable } from '@angular/core';
import { Mail } from 'src/app/entities/mail';
import { environment } from 'src/environments/environment';
import { CommonService } from '../common/common-service';

const axios = require('axios');

@Injectable({
    providedIn: 'root',
})
export class MailService {
    constructor(public commonService: CommonService) {}

    async sendMail(content: Mail) {
        if (!(await this.commonService.checkNetworkStatus())) {
            return;
        }
        try {
            const headers = { 'Content-Type': 'text/plain' };

            const response = await axios({
                method: 'post',
                headers,
                url: `${environment.apiUrl}/api/mail/send`,
                data: content,
                responseType: 'json',
            });
            return response;
        } catch (error) {
            return error.response;
        }
    }
}
