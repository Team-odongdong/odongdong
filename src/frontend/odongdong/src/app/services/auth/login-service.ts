import { Injectable } from "@angular/core";

import axios from "axios";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root',
})
export class LoginService {
    constructor() {}

    async kakaoLogin() {
        try {
            const response = await axios({
                method: 'get',
                url: `https://kauth.kakao.com/oauth/authorize?client_id=dcbe17d69b2ae21c9daf02612a3b2b9a&redirect_uri=http://${environment.apiUrl}/login/oauth2/code/kakao&response_type=code`,
                responseType: 'json',
            });
            return response;
        } catch(error) {
            return error.response;
        }
    }

}