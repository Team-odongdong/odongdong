import { Component, OnInit } from '@angular/core';
import { ModalController, NavController } from '@ionic/angular';
import { CommonService } from 'src/app/services/commonService';

@Component({
  selector: 'app-profile',
  templateUrl: 'profile.page.html',
  styleUrls: ['profile.page.scss'],
})
export class ProfilePage implements OnInit {
  constructor(
    private common: CommonService,
    private navController: NavController
  ) {}

  ngOnInit() {}

  async ionViewDidEnter() {
    await this.common.notOpenedFeatureAlert();
    this.navController.navigateBack('/tabs/main');
  }

  onClickBackButton() {
    this.navController.navigateBack('/tabs/main');
  }
}
