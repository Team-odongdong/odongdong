import { Component, OnInit } from '@angular/core';
import { ModalController, NavController } from '@ionic/angular';
import { SocialLoginComponent } from 'src/app/modals/social-login/social-login.component';

@Component({
  selector: 'app-profile',
  templateUrl: 'profile.page.html',
  styleUrls: ['profile.page.scss']
})
export class ProfilePage implements OnInit {

  constructor(
    public navController: NavController,
    public modalController: ModalController,
  ) {}

  ngOnInit(): void {}

  onClickBackButton() {
    this.navController.navigateBack('/tabs/main');
  }

  async openLogin() {
    const modal = await this.modalController.create({
      component: SocialLoginComponent,
    });
    await modal.present();
  }
  
}
