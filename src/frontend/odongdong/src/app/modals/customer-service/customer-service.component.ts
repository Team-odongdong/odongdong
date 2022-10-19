import { Component, OnInit } from '@angular/core';

import { ModalController, ToastController } from '@ionic/angular';
import { MailService } from 'src/app/services/mail/mail.service';

@Component({
  selector: 'app-customer-service',
  templateUrl: './customer-service.component.html',
  styleUrls: ['./customer-service.component.scss'],
})
export class CustomerServiceComponent implements OnInit {
  public customerServiceContent;

  constructor(
    public mailService: MailService,
    public modalController: ModalController,
    public toastController: ToastController,
  ) { }

  ngOnInit() {}

  onClickCloseButton() {
    this.modalController.dismiss();
  }

  async onClickSubmitButton() {
    const response = await this.mailService.sendMail(
      this.customerServiceContent
    );

    if(response.status === 200) {
      this.successSendMail();
      this.modalController.dismiss();
    } else {
      this.failSendMail();
    }
  }

  async successSendMail() {
    const toast = await this.toastController.create({
      message: "문의 내용이 전송되었습니다!",
      duration: 1500,
    });
    await toast.present();
  }

  async failSendMail() {
    const toast = await this.toastController.create({
      message: "전송에 실패했습니다.",
      duration: 1500,
    });
    await toast.present();
  }
}
