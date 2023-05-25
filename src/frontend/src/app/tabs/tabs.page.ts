import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { IonTabs, ModalController } from '@ionic/angular';
import { AddBathroomComponent } from '../components/add-bathroom/add-bathroom.component';
import { StorageService } from '../services/storageService';
import { AuthService } from '../services/authService';

@Component({
  selector: 'app-tabs',
  templateUrl: 'tabs.page.html',
  styleUrls: ['tabs.page.scss'],
})
export class TabsPage {
  @ViewChild('tabs', { static: false })
  tabs: IonTabs;

  public addMarkerIcon = '../assets/svgs/tab/add-marker.svg';
  public mapIcon = '../assets/svgs/tab/map.svg';
  public userProfileIcon = '../assets/svgs/tab/user-profile.svg';
  public settingsIcon = '../assets/svgs/tab/settings.svg';

  public currentLocation: any;

  constructor(
    private router: Router,
    private modal: ModalController,
    private storage: StorageService,
    private auth: AuthService
  ) {}

  ionViewDidEnter() {
    navigator.geolocation.getCurrentPosition((position) => {
      this.currentLocation = position;
    });

    this.checkId();
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

  async checkId() {
    const id = await this.storage.getStorage('UUID');
    const name = await this.storage.getStorage('TOKEN_NAME');

    if (!id || !name) {
      this.createId();
    }
  }

  async createId() {
    const response = await this.auth.createUUID();

    this.storage.setStorage('UUID', response.data.result.uuid);
    this.storage.setStorage('TOKEN_NAME', response.data.result.name);
  }

  async deleteId() {
    await this.storage.removeStorage('UUID');
    await this.storage.removeStorage('TOKEN_NAME');
  }
}
