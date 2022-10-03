import { Component, OnInit } from '@angular/core';

import { ModalController, ToastController } from '@ionic/angular';

import { Geolocation } from '@capacitor/geolocation';

import { BathroomService } from 'src/app/services/bathroom/bathroom.service';

@Component({
  selector: 'app-add-bathroom',
  templateUrl: './add-bathroom.component.html',
  styleUrls: ['./add-bathroom.component.scss'],
})
export class AddBathroomComponent implements OnInit {
  public latitude;
  public longitude;
  public bathroomName;
  public bathroomAddress;
  public bathroomAddressDetail;
  public bathroomImageUrl;
  public rate;

  public currentLat;
  public currentLng;

  constructor(
    public bathroomService: BathroomService,
    public toastController: ToastController,
    public modalController: ModalController,
  ) { }

  ngOnInit() {}

  async ionViewDidEnter() {
    await this.getCurrentLocation();
  }

  async getCurrentLocation () {
    const loc = await Geolocation.getCurrentPosition({
      enableHighAccuracy: true,
      timeout: 1000,
    });

    if(loc.timestamp > 0) {
      this.setLatLng(loc);
    } else {
      console.log('fail to get current location');
    }        
  }

  setLatLng(loc: any){
    this.currentLat = loc.coords.latitude;
    this.currentLng = loc.coords.longitude;
  }

  bathroomInformation() {
    console.log('enrolling location', this.currentLat, this.currentLng);
    
    const info = {
      latitude: this.currentLat,
      longitude: this.currentLng,
      title: this.bathroomName,
      isLocked: 'N',
      address: this.bathroomAddress,
      addressDetail: this.bathroomAddressDetail,
      rate: this.rate,
    }

    return info;
  }
  
  onRatingChange(rating){
    this.rate = rating;
    console.log('changed rating: ',rating);
  }

  async onClickSaveButton() {
    const info = this.bathroomInformation();

    const response = await this.bathroomService.addBathroom(
      info
    );

    if(response.status === 200) {
      this.successAddBathroom();
    } else {
      this.failAddBathroom();
    }
  }
  
  async successAddBathroom() {
    const toast = await this.toastController.create({
      message: '화장실 정보가 등록되었습니다!',
      duration: 1500,
    });
    await toast.present();
    await this.modalController.dismiss();
  }

  async failAddBathroom() {
    const toast = await this.toastController.create({
      message: '화장실 등록 실패',
      duration: 1500,
    });
    await toast.present();
  }
}
