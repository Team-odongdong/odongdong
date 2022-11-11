import { Component, OnInit } from '@angular/core';
import { ModalController, NavController } from '@ionic/angular';
import { LoginService } from 'src/app/services/auth/login-service';

@Component({
  selector: 'app-social-login',
  templateUrl: './social-login.component.html',
  styleUrls: ['./social-login.component.scss'],
})
export class SocialLoginComponent implements OnInit {

  constructor(
    public loginService: LoginService,
    public modalController: ModalController,
    public navController: NavController,
  ) { }

  ngOnInit() {}

  onClickBackButton() {
    this.navController.navigateBack('/tabs/main');
    this.modalController.dismiss();
  }

  async kakaoLogin() {
    const response = await this.loginService.kakaoLogin();

    console.log(response);
    
  }
}
