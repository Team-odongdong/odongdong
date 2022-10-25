import { Component, Input, OnInit } from '@angular/core';

import { ModalController, ToastController } from '@ionic/angular';

import { Geolocation } from '@capacitor/geolocation';

import { BathroomService } from 'src/app/services/bathroom/bathroom-service';

@Component({
  selector: 'app-add-bathroom',
  templateUrl: './add-bathroom.component.html',
  styleUrls: ['./add-bathroom.component.scss'],
})
export class AddBathroomComponent implements OnInit {
  @Input() lat: number;
  @Input() lng: number;

  public currentLat: number;
  public currentLng: number;

  public bathroomName: string;
  public bathroomAddress: string;
  public bathroomAddressDetail: string;
  public bathroomImageUrl: string;
  public rate;
  public isUnisex = false;

  public isValid = true;

  constructor(
    public bathroomService: BathroomService,
    public toastController: ToastController,
    public modalController: ModalController,
  ) { }

  ngOnInit() {}

  ionViewDidEnter() {
    // this.getCurrentLocation();
    console.log('from modal', this.lat, this.lng);
    
  }

  async getCurrentLocation() {
    //add bathroom with clicked marker location
    if(this.lat) {
      await this.getAddressWithLatLng(this.lat, this.lng);
    } else { //add bathroom with current location
      const currentLocation = await Geolocation.getCurrentPosition();
      await this.getAddressWithLatLng(currentLocation.coords.latitude, currentLocation.coords.longitude);
    }
  }
  
  async getAddressWithLatLng(lat: number, lng: number) {
    const response = await this.bathroomService.getAddressName(
      lat,
      lng,
    );

    if(response.data.code === 1000) {      
      this.fetchBathroomAddress(response.data);
    } else {
      this.failToGetBathroomAddress();
    }
  }

  fetchBathroomAddress(data) {
    this.bathroomAddress = data.address_name;
    this.bathroomAddressDetail = data.address_detail;
  }

  async failToGetBathroomAddress() {
    const toast = await this.toastController.create({
      header: '서버에서 주소를 가져오지 못했습니다.',
      message: '직접 주소를 입력해주세요!',
      duration: 4000,
    });
    await toast.present();
  }

  bathroomInformation() {
    if(!this.lat) {
      this.lat = this.currentLat;
      this.lng = this.currentLng;
    }
    
    const info = {
      latitude: this.lat,
      longitude: this.lng,
      title: this.bathroomName,
      isLocked: 'N',
      address: this.bathroomAddress,
      addressDetail: this.bathroomAddressDetail,
      rate: this.rate,
      isUnisex: this.isUnisex,
    }

    return info;
  }
  
  onRatingChange(rating){
    this.rate = rating;
  }

  async onClickSaveButton() {
    if(!this.checkAddValidation()) {
      this.failValidationToast();
      return;
    }

    const info = this.bathroomInformation();
    const response = await this.bathroomService.addBathroom(
      info
    );

    if(response.data.code === 1000) {
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

  checkAddValidation() {
    if(!this.bathroomName || !this.bathroomAddress) {
      this.isValid = false;
      return false;
    }

    this.isValid = true;
    return true;
  }

  async failValidationToast() {
    const toast = await this.toastController.create({
      message: '필수 항목들을 입력해주세요',
      duration: 1500,
    });
    await toast.present();
  }

  checkIsUnisex() {
    this.isUnisex = this.isUnisex? false: true;    
  }
}
