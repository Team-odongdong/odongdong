import { Component, OnInit } from '@angular/core';

import { ModalController, NavController } from '@ionic/angular';
import { CustomerServiceComponent } from 'src/app/modals/customer-service/customer-service.component';

import { PolicyComponent } from 'src/app/modals/policy/policy.component';
import { Constants } from 'src/constants';

@Component({
  selector: 'app-setting',
  templateUrl: 'setting.page.html',
  styleUrls: ['setting.page.scss'],
})
export class SettingPage implements OnInit {
  public app_version: string;

  constructor(
    public navController: NavController,
    public modalController: ModalController
  ) {}

  ngOnInit(): void {}

  ionViewDidEnter() {
    this.app_version = Constants.APP_VERSION;
  }

  onClickBackButton() {
    this.navController.navigateBack('/tabs/main');
  }

  async onClickPolicy() {
    const modal = await this.modalController.create({
      component: PolicyComponent,
    });
    await modal.present();
  }

  async onClickCustomerService() {
    const modal = await this.modalController.create({
      component: CustomerServiceComponent,
    });
    await modal.present();
  }
}
