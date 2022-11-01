import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { Camera, CameraResultType } from '@capacitor/camera';
import { Device } from '@capacitor/device';

import { AlertController, ModalController, ToastController } from '@ionic/angular';

import { BathroomService } from 'src/app/services/bathroom/bathroom-service';

@Component({
  selector: 'app-add-bathroom',
  templateUrl: './add-bathroom.component.html',
  styleUrls: ['./add-bathroom.component.scss'],
})
export class AddBathroomComponent implements OnInit {
  @ViewChild('imageInput') imageInput: any;

  @Input() lat: number;
  @Input() lng: number;
  @Input() currentLat: number;
  @Input() currentLng: number;

  public bathroomName: string;
  public bathroomAddress: string;
  public bathroomAddressDetail: string;
  public bathroomImageUrl: string;
  public rate;
  public isUnisex = false;

  public isValid = true;

  public imageList = [];

  public userPlatform;

  constructor(
    public bathroomService: BathroomService,
    public toastController: ToastController,
    public modalController: ModalController,
    public alertController: AlertController,
  ) { }

  ngOnInit() {}

  async ionViewDidEnter() {
    await this.setPlatForm();
    
    if(!this.lat) {
      await this.addBathroomAtCurrentAlert();
      await this.getAddressWithLatLng(this.currentLat, this.currentLng);
    } else {
      await this.getAddressWithLatLng(this.lat, this.lng);
    }

  }

  async setPlatForm() {
    const info = await Device.getInfo();
    this.userPlatform = info.platform;
    console.log('user', this.userPlatform);
  }

  /** 추가 마커가 선택되어 있지 않은 경우에는 alert 창 띄워주기 */
  async addBathroomAtCurrentAlert() {
    const alert = await this.alertController.create({
      message: '마커를 등록하지 않으면, 현재 위치로 화장실이 등록됩니다!',
      buttons: [
        {
          text: '확인했어요',
          handler: () => {}
        }
      ]
    });
    await alert.present();
  }
  
  async getAddressWithLatLng(lat: number, lng: number) {
    const response = await this.bathroomService.getAddressName(
      lat,
      lng,
    );

    if(response.data.code === 1000) {
      this.fetchBathroomAddress(response.data.result);
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

  async takePictureOrOpenLibrary() {
    if(this.userPlatform === 'web') {
      this.imageInput.nativeElement.click();
      const fileList = this.imageInput.nativeElement.files;
      
    }



    const image = await Camera.getPhoto({
      quality: 70,
      allowEditing: true,
      resultType: CameraResultType.Uri,
      promptLabelPhoto: '앨범에서 선택',
      promptLabelPicture: '사진 찍기',
      promptLabelCancel: '취소'
    });

    // const imageUrl = image.webPath;

    this.imageList.push(image.webPath);

    console.log('imageList', this.imageList);
    
  }
}
