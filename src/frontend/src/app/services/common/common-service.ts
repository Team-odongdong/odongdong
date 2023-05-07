import { Injectable } from '@angular/core';
import { Network } from '@capacitor/network';
import { AlertController, ModalController, ToastController } from '@ionic/angular';
import { LoginService } from '../auth/login-service';

@Injectable({
    providedIn: 'root',
})
export class CommonService {
    constructor(
        public loginService: LoginService,
        public toastController: ToastController,
        public alertController: AlertController,
        public modalController: ModalController,
    ) {}

    async checkNetworkStatus() {
        const status = await Network.getStatus();
        if (status.connected) {
            return true;
        }

        const toast = await this.toastController.create({
            message: '네트워크가 오프라인 상태입니다.',
            duration: 2000,
        });
        await toast.present();

        return false;
    }

    closePresentModal() {
        this.modalController.getTop().then((v) => {
            v ? this.modalController.dismiss() : {};
        });
    }

    async isLogin() {
        try {
            const response = await this.loginService.getUserProfile();

            return response.data.code === 1000 ? true : false;
        } catch (error) {
            return false;
        }
    }

    async notOpenedFeatureAlert() {
        const alert = await this.alertController.create({
            header: '출시 예정인 기능입니다',
            message: '다음 버전을 기대해주세요!',
            buttons: [
                {
                    text: '닫기',
                    handler: () => {},
                },
            ],
        });
        await alert.present();
    }
}
