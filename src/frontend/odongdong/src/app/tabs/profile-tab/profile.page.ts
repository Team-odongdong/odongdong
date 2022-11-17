import { Component, OnInit } from '@angular/core';
import { ModalController, NavController } from '@ionic/angular';
import { SocialLoginComponent } from 'src/app/modals/social-login/social-login.component';

@Component({
  selector: 'app-profile',
  templateUrl: 'profile.page.html',
  styleUrls: ['profile.page.scss']
})
export class ProfilePage implements OnInit {
  public accessToken: string;

  constructor(
    public navController: NavController,
    public modalController: ModalController,
  ) {}

  ngOnInit(): void {}

  ionViewDidEnter() {
    if(!this.accessToken) {
      // this.openSocialLogin();
    }
  }

  onClickBackButton() {
    this.navController.navigateBack('/tabs/main');
  }

  async openSocialLogin() {
    const modal = await this.modalController.create({
      component: SocialLoginComponent,
    });
    await modal.present();
  }
}
