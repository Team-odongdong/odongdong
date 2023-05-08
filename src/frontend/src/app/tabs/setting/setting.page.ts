import { Component, OnInit } from '@angular/core';
import { ModalController, NavController } from '@ionic/angular';
import { CustomerServiceComponent } from 'src/app/components/customer-service/customer-service.component';
import { PolicyComponent } from 'src/app/components/policy/policy.component';
import { APP_VERSION } from 'src/constants';

@Component({
  selector: 'app-setting',
  templateUrl: 'setting.page.html',
  styleUrls: ['setting.page.scss'],
})
export class SettingPage implements OnInit {
  public appVersion = APP_VERSION;

  constructor(
    public modal: ModalController,
    public navController: NavController
  ) {}

  ngOnInit() {}

  onClickBackButton() {
    this.navController.navigateBack('/tabs/main');
  }

  async onClickPolicy() {
    const modal = await this.modal.create({
      component: PolicyComponent,
    });
    await modal.present();
  }

  async onClickCustomerService() {
    const modal = await this.modal.create({
      component: CustomerServiceComponent,
    });
    await modal.present();
  }
}
