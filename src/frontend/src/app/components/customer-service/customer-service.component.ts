import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { CommonService } from 'src/app/services/commonService';
import { MailService } from 'src/app/services/mailService';

@Component({
  selector: 'app-customer-service',
  templateUrl: './customer-service.component.html',
  styleUrls: ['./customer-service.component.scss'],
})
export class CustomerServiceComponent implements OnInit {
  public customerServiceContent = '';

  constructor(
    private mail: MailService,
    private modal: ModalController,
    private common: CommonService
  ) {}

  ngOnInit() {}

  onClickCloseButton() {
    this.modal.dismiss();
  }

  async onClickSubmitButton() {
    try {
      const response = await this.mail.sendMail(this.customerServiceContent);

      if (response.status === 200) {
        this.common.showToast('문의 내용이 전송되었습니다!', 1500);
        this.modal.dismiss();
      } else {
        this.common.showToast('전송에 실패했습니다.', 1500);
      }
    } catch (error) {
      await this.common.showAlert(error as string);
    }
  }
}
