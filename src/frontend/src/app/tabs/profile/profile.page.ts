import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { AuthService } from 'src/app/services/authService';
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

  constructor(
    private auth: AuthService,
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
      this.registered = data.result.allRegisterBathroomNum;
      this.notRegistered = data.result.notRegisterBathroomNum;
    }
  }
}
