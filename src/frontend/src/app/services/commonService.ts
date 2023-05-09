import { Injectable } from '@angular/core';
import { Network } from '@capacitor/network';
import {
  AlertController,
  ModalController,
  ToastController,
} from '@ionic/angular';

@Injectable({
  providedIn: 'root',
})
export class CommonService {
  constructor(
    private toast: ToastController,
    private modal: ModalController,
    private alert: AlertController
  ) {}

  async checkNetworkStatus() {
    const status = await Network.getStatus();
    if (status.connected) {
      return true;
    }

    const toast = await this.toast.create({
      message: '네트워크가 오프라인 상태입니다.',
      duration: 2000,
    });
    await toast.present();

    return false;
  }

  closePresentModal() {
    this.modal.getTop().then((val) => {
      val ? this.modal.dismiss() : {};
    });
  }

  async notOpenedFeatureAlert() {
    const alert = await this.alert.create({
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

  async showAlert(message: string, header?: string) {
    const alert = await this.alert.create({
      header: header,
      message: message,
      buttons: [
        {
          text: '닫기',
          handler: () => {},
        },
      ],
    });

    await alert.present();
  }

  async showToast(message: string, duration: number, header?: string) {
    const toast = await this.toast.create({
      header,
      message,
      duration,
    });

    await toast.present();
  }
}
