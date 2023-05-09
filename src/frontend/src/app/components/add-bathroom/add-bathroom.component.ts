import {
  ChangeDetectorRef,
  Component,
  Input,
  OnInit,
  ViewChild,
} from '@angular/core';
import { Camera, CameraResultType } from '@capacitor/camera';
import { Device } from '@capacitor/device';
import { Filesystem } from '@capacitor/filesystem';
import {
  AlertController,
  LoadingController,
  ModalController,
} from '@ionic/angular';
import { BathroomService } from 'src/app/services/bathroomService';
import { CommonService } from 'src/app/services/commonService';
import { BathroomInfo } from 'src/app/types/bathroomInfo';
import { LatLng } from 'src/app/types/location';
import { b64ToBlob } from 'src/app/utils/imageConverter';

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
  public rate = 0;
  public isUnisex = false;

  public isValid = true;

  public imageList: any[] = [];
  public imageListForDisplay: any[] = [];

  public userPlatform = 'web';

  constructor(
    private bathroomService: BathroomService,
    private common: CommonService,
    private modal: ModalController,
    private alert: AlertController,
    private loading: LoadingController,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  ngOnInit() {}

  async ionViewDidEnter() {
    await this.setPlatForm();

    if (!this.lat) {
      await this.addBathroomAtCurrentAlert();
      await this.getAddressWithLatLng({
        lat: this.currentLat,
        lng: this.currentLng,
      });
    } else {
      await this.getAddressWithLatLng({ lat: this.lat, lng: this.lng });
    }
  }

  async setPlatForm() {
    const info = await Device.getInfo();
    this.userPlatform = info.platform;
  }

  async addBathroomAtCurrentAlert() {
    const alert = await this.alert.create({
      message: '마커를 등록하지 않으면, 현재 위치로 화장실이 등록됩니다!',
      buttons: [
        {
          text: '확인했어요',
          handler: () => {},
        },
      ],
    });
    await alert.present();
  }

  async getAddressWithLatLng({ lat, lng }: LatLng) {
    try {
      const response = await this.bathroomService.getAddressName({ lat, lng });

      if (response.data.code === 1000) {
        this.fetchBathroomAddress(response.data.result);
      } else {
        await this.common.showToast(
          '직접 주소를 입력해주세요!',
          4000,
          '서버에서 주소를 가져오지 못했습니다.'
        );
      }
    } catch (error) {
      this.common.showAlert(error as string);
    }
  }

  fetchBathroomAddress(data: any) {
    this.bathroomAddress = data.address_name;
    this.bathroomAddressDetail = data.address_detail;
  }

  bathroomInformation() {
    if (!this.lat) {
      this.lat = this.currentLat;
      this.lng = this.currentLng;
    }

    const info: BathroomInfo = {
      lat: this.lat,
      lng: this.lng,
      title: this.bathroomName,
      isLocked: 'N',
      address: this.bathroomAddress,
      addressDetail: this.bathroomAddressDetail,
      rate: this.rate,
      isUnisex: this.isUnisex,
      imageUrl: '',
    };

    return info;
  }

  onRatingChange(event: any) {
    this.rate = event.rate;
  }

  async onClickSaveButton() {
    if (!this.checkAddValidation()) {
      this.common.showToast('필수 항목들을 입력해주세요', 1500);
      return;
    }

    const loading = await this.loading.create({
      message: '화장실 등록 요청중입니다...',
    });

    try {
      const info = this.bathroomInformation();
      const response = await this.bathroomService.addBathroom(
        info,
        this.imageList
      );

      await loading.dismiss();

      if (response.data.code === 1000) {
        await this.common.showToast('화장실 정보가 등록되었습니다!', 1500);
        await this.modal.dismiss();
      } else {
        this.common.showToast('화장실 등록 실패', 1500);
      }
    } catch (error) {
      this.common.showAlert(error as string);
    }
  }

  checkAddValidation() {
    if (!this.bathroomName || !this.bathroomAddress) {
      this.isValid = false;
      return false;
    }

    this.isValid = true;
    return true;
  }

  checkIsUnisex() {
    this.isUnisex = this.isUnisex ? false : true;
  }

  makeImageOnlyOne() {
    if (this.imageList.length && this.imageListForDisplay.length) {
      this.imageList = [];
      this.imageListForDisplay = [];
      this.changeDetectorRef.detectChanges();
    }
  }

  async takePictureOrOpenLibrary() {
    this.makeImageOnlyOne();

    const imageData = {
      fileName: undefined,
      fileFormat: undefined,
      fileBlob: undefined,
    };
    const displayImageData = {
      imageName: undefined,
      image: undefined,
    };

    if (this.userPlatform === 'web') {
      //on web
      this.imageInput.nativeElement.click();
      const imageList = this.imageInput.nativeElement.files;

      await this.onFileChange(imageList);
    } else {
      //on mobile
      const imageData: any = {
        fileName: undefined,
        fileFormat: undefined,
        fileBlob: undefined,
      };
      const displayImageData: any = {
        imageName: undefined,
        image: undefined,
      };

      const image = await Camera.getPhoto({
        quality: 30,
        allowEditing: false,
        resultType: CameraResultType.Uri,
        promptLabelHeader: '사진 첨부',
        promptLabelPhoto: '앨범에서 선택',
        promptLabelPicture: '사진 찍기',
        promptLabelCancel: '취소',
      });

      const imagePath =
        this.userPlatform === 'web' ? image.webPath : image.path;
      const splitedImagePath = imagePath?.split('/') || '';
      const imageName = imagePath?.split('/')[splitedImagePath.length - 1];
      const imageBase64 = await Filesystem.readFile({ path: imagePath || '' });
      const blob = b64ToBlob({
        b64Data: imageBase64,
        contentType: `image/${image.format}`,
      });

      imageData.fileName = imageName;
      imageData.fileFormat = image.format;
      imageData.fileBlob = blob;
      displayImageData.imageName = imageName;
      displayImageData.image = `data:image/${image.format};base64,${imageBase64.data}`;

      this.imageListForDisplay.push(displayImageData);
      this.imageList.push(imageData);
    }

    this.changeDetectorRef.detectChanges();
  }

  async onFileChange(files: any) {
    const imageData = {
      fileName: undefined,
      fileFormat: undefined,
      fileBlob: undefined,
    };
    const displayImageData: any = {
      imageName: undefined,
      image: undefined,
    };
    const fileExtension = /(.*?)\.(webp)$/;

    for (let i = 0; i < files.length; i++) {
      const fileInfo = files.item(i);
      const imageName = fileInfo.name;
      const imageType = fileInfo.type;
      const extension = imageType.split('/')[1];
      if (files.item(i).name.match(fileExtension)) {
        await this.common.showAlert(
          'webp 형식의 이미지 파일은 업로드 할 수 없습니다.'
        );
        return;
      }

      const reader = new FileReader();

      reader.readAsDataURL(files.item(i));

      reader.onload = () => {
        displayImageData.imageName = imageName;
        displayImageData.image = reader.result;
        imageData.fileBlob = fileInfo;
        imageData.fileFormat = extension;
        imageData.fileName = imageName;
        this.imageListForDisplay.push(displayImageData);
        this.imageList.push(imageData);
        this.changeDetectorRef.detectChanges();
      };
    }

    this.imageInput.nativeElement.value = '';

    // const file = files[0];
    // const reader = new FileReader();

    // let imageSrc;

    // reader.readAsDataURL(file);
    // reader.onload = () => {
    //   imageSrc = reader.result;
    //   // this.imageListForDisplay.push(displayImageData);
    //   // this.imageList.push(imageData);
    //   this.changeDetectorRef.detectChanges();
    // }

    this.changeDetectorRef.detectChanges();
  }

  async deleteImage(imageIndex: any) {
    const alert = await this.alert.create({
      subHeader: '삭제하시겠습니까?',
      buttons: [
        {
          text: '취소',
          handler: () => {},
        },
        {
          text: '확인',
          handler: () => {
            const updateImageList = [...this.imageList];
            const updateImageListForDisplay = [...this.imageListForDisplay];

            updateImageList.splice(imageIndex, 1);
            updateImageListForDisplay.splice(imageIndex, 1);

            this.imageList = updateImageList;
            this.imageListForDisplay = updateImageListForDisplay;

            this.changeDetectorRef.detectChanges();
          },
        },
      ],
    });
    await alert.present();
  }
}
