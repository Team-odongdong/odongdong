import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import {
  AlertController,
  IonTabs,
  ModalController,
  ToastController,
} from '@ionic/angular';
import { AddBathroomComponent } from '../components/add-bathroom/add-bathroom.component';

@Component({
  selector: 'app-tabs',
  templateUrl: 'tabs.page.html',
  styleUrls: ['tabs.page.scss'],
})
export class TabsPage {
  @ViewChild('tabs', { static: false })
  tabs: IonTabs;

  public addMarkerIcon = '../assets/svg/tab/add-marker.svg';
  public mapIcon = '../assets/svg/tab/map.svg';
  public userProfileIcon = '../assets/svg/tab/user-profile.svg';
  public settingsIcon = '../assets/svg/tab/settings.svg';

  public currentLocation: any;

  constructor(private router: Router, private modal: ModalController) {}

  ionViewDidEnter() {
    navigator.geolocation.getCurrentPosition((position) => {
      this.currentLocation = position;
    });
  }

  async showEnrollComponent() {
    this.router.navigateByUrl('/tabs/main');
    const modal = await this.modal.create({
      component: AddBathroomComponent,
      componentProps: {
        currentLat: this.currentLocation.coords.latitude,
        currentLng: this.currentLocation.coords.longitude,
      },
      showBackdrop: false,
      cssClass: 'add-bathroom-modal',
      canDismiss: true,

      breakpoints: [0, 0.5, 0.75, 1],
      initialBreakpoint: 0.75,
      backdropDismiss: false,
      backdropBreakpoint: 0.75,
    });
    await modal.present();
  }

  currentTab() {
    if (this.tabs.getSelected() === 'profile') {
      this.onClickProfile();
    }
  }

  async onClickProfile() {
    // const alert = await this.alertController.create({
    //   message: '다음 버전을 기대해주세요!',
    //   buttons: [
    //     {
    //       text: '닫기',
    //       handler: () => {
    //         this.location.back();
    //       }
    //     },
    //   ],
    // });
    // await alert.present();
  }
}
