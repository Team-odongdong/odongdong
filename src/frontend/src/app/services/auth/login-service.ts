import { Injectable } from '@angular/core';
import { AlertController, NavController } from '@ionic/angular';

import axios from 'axios';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root',
})
export class LoginService {
    constructor(public navController: NavController, public alertController: AlertController) {}

    async kakaoLogin() {
        location.replace(`${environment.apiUrl}/oauth2/authorization/kakao`);
    }

    async getUserProfile() {
        try {
            const response = await axios({
                method: 'get',
                withCredentials: true,
                url: `${environment.apiUrl}/api/user/profile`,
                responseType: 'json',
            });
            return response;
        } catch (error) {
            return error.response;
        }
    }

    async needLoginAlert(close?: boolean) {
        const alert = await this.alertController.create({
            header: '로그인이 필요합니다!',
            buttons: [
                {
                    text: '로그인 하기',
                    handler: () => {
                        this.navController.navigateForward('/tabs/profile');
                    },
                },
                {
                    text: '수정 취소',
                    handler: () => {
                        if (!close) {
                            this.navController.back();
                        }
                    },
                },
            ],
        });
        await alert.present();
    }
}
