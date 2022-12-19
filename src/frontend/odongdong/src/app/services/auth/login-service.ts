import { Injectable } from '@angular/core';
import { NavController } from '@ionic/angular';

import axios from 'axios';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root',
})
export class LoginService {
    constructor(public navController: NavController) {}

    async kakaoLogin() {
        location.replace(`${environment.apiUrl}/oauth2/authorization/kakao`);
    }

    async getUserProfile() {
        try {
            const response = await axios({
                method: 'get',
                url: `${environment.apiUrl}/api/user/profile`,
                withCredentials: true,
                responseType: 'json',
            });
            return response;
        } catch (error) {
            return error.response;
        }
    }
}
