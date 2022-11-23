import { Injectable } from "@angular/core";
import { NavController } from "@ionic/angular";

import axios from "axios";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root',
})
export class LoginService {
    constructor(
        public navController: NavController,
    ) {}

    async kakaoLogin() {
        location.replace(`${environment.apiUrl}/oauth2/authorization/kakao`);
    }


    // async cookieTest(temp) {
    //     console.log('seding', temp);
        
    //     try {
    //         const response = await axios({
    //             method: 'get',
    //             url: "https://prod.odongdong.site/api/user/test",
    //         });
    //         console.log('response', response);
            
    //         return response;
    //     } catch(error) {
    //         return error.response;
    //     }
    // }
}