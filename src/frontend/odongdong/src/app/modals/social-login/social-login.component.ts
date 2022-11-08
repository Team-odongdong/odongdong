import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'app-social-login',
  templateUrl: './social-login.component.html',
  styleUrls: ['./social-login.component.scss'],
})
export class SocialLoginComponent implements OnInit {

  constructor(
    public modalController: ModalController,
  ) { }

  ngOnInit() {}

  onClickBackButton() {
    this.modalController.dismiss();
  }
}
