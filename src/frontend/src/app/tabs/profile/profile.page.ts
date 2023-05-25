import { Component, OnInit } from '@angular/core';
import { NavigationExtras } from '@angular/router';
import { NavController } from '@ionic/angular';
import { AuthService } from 'src/app/services/authService';
import { CommonService } from 'src/app/services/commonService';
import { StorageService } from 'src/app/services/storageService';

@Component({
  selector: 'app-profile',
  templateUrl: 'profile.page.html',
  styleUrls: ['profile.page.scss'],
})
export class ProfilePage implements OnInit {
  public userName = '';
  public registered = 0;
  public notRegistered = 0;
  public notRegisteredList: string[] = [];

  constructor(
    private auth: AuthService,
    private common: CommonService,
    private storage: StorageService,
    private navController: NavController
  ) {}

  ngOnInit() {
    this.getUserName().then(() => {
      this.getProfileInfo();
    });
  }

  onClickBackButton() {
    this.navController.navigateBack('/tabs/main');
  }

  async getUserName() {
    const name = await this.storage.getStorage('TOKEN_NAME');

    if (name) {
      this.userName = name;
    }
  }

  async getProfileInfo() {
    const { data } = await this.auth.getProfile();

    if (data.code === 1000) {
      this.registered = data.result.registerBathroomNum;
      this.notRegistered = data.result.notRegisterBathroomNum;
      this.notRegisteredList = data.result.notRegisterBathroomTitles;
    }
  }

  async emptyListAlert() {
    await this.common.showAlert('등록한 화장실이 없습니다.');
  }

  async goRegisteredList() {
    if (this.registered === 0) {
      await this.emptyListAlert();
    } else {
      this.navController.navigateForward('/registered-list');
    }
  }

  async showNotRegisteredList() {
    if (this.notRegistered === 0) {
      await this.emptyListAlert();
    }
  }
}
