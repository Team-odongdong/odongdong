import { Location } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';

import { AlertController, IonTabs, ModalController } from '@ionic/angular';

import { AddBathroomComponent } from '../modals/add-bathroom/add-bathroom.component';

@Component({
  selector: 'app-tabs',
  templateUrl: 'tabs.page.html',
  styleUrls: ['tabs.page.scss']
})
export class TabsPage {
  @ViewChild('tabs', { static: false })
  tabs: IonTabs;
  
  public addMarkerIcon = '../assets/svg/tab/add-marker.svg';
  public mapIcon = '../assets/svg/tab/map.svg';
  public userProfileIcon = '../assets/svg/tab/user-profile.svg';
  public settingsIcon = '../assets/svg/tab/settings.svg';

  constructor(
    public router: Router,
    public location: Location,
    public alertController: AlertController,
    public modalController: ModalController,
  ) {}

  async showEnrollComponent() {
    this.router.navigateByUrl('/tabs/main');
    const modal = await this.modalController.create({
      component: AddBathroomComponent,
      showBackdrop: false,
      cssClass: 'add-bathroom-modal',
      canDismiss: true,
      
      breakpoints: [0, 0.5, 0.75],
      initialBreakpoint: 0.75,
      backdropDismiss: false,
      backdropBreakpoint: 0.75,
    });
    await modal.present();
  }

  currentTab() {
    console.log(this.tabs.getSelected());
    if (this.tabs.getSelected() === 'profile') {
      this.onClickProfile();
    }
  }

  async onClickProfile() {
    const alert = await this.alertController.create({
      message: '다음 버전을 기대해주세요!',
      buttons: [
        {
          text: '닫기',
          handler: () => {
            this.location.back();
          }
        },
      ],
    });
    await alert.present();
  }
}
