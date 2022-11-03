import { Injectable } from "@angular/core";
import { Network } from "@capacitor/network";
import { AlertController, ToastController } from "@ionic/angular";

@Injectable({
    providedIn: 'root',
})
export class CommonService {
    
    constructor(
        public toastController: ToastController,
        public alertController: AlertController,
    ) {}

    async checkNetworkStatus() {
        const status = await Network.getStatus();
        if(status.connected) return true;

        const toast = await this.toastController.create({
            message: "네트워크가 오프라인 상태입니다.",
            duration: 2000,
        });
        await toast.present();

        return false;
    }
}