import { ChangeDetectorRef, Component, Input, OnInit, ViewChild } from '@angular/core';

import { Camera, CameraResultType } from '@capacitor/camera';
import { Device } from '@capacitor/device';
import { Filesystem } from '@capacitor/filesystem';

import {
    AlertController,
    LoadingController,
    ModalController,
    ToastController,
} from '@ionic/angular';

import { BathroomService } from 'src/app/services/bathroom/bathroom-service';
import { ImageService } from 'src/app/services/image/image-service';

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
    public imageListForDisplay = [];

    public userPlatform: string;

    constructor(
        public imageService: ImageService,
        public bathroomService: BathroomService,
        public toastController: ToastController,
        public modalController: ModalController,
        public alertController: AlertController,
        public loadingController: LoadingController,
        public changeDetectorRef: ChangeDetectorRef,
    ) {}

    ngOnInit() {}

    async ionViewDidEnter() {
        await this.setPlatForm();

        if (!this.lat) {
            await this.addBathroomAtCurrentAlert();
            await this.getAddressWithLatLng(this.currentLat, this.currentLng);
        } else {
            await this.getAddressWithLatLng(this.lat, this.lng);
        }
    }

    async setPlatForm() {
        const info = await Device.getInfo();
        this.userPlatform = info.platform;
    }

    /** ?????? ????????? ???????????? ?????? ?????? ???????????? alert ??? ???????????? */
    async addBathroomAtCurrentAlert() {
        const alert = await this.alertController.create({
            message: '????????? ???????????? ?????????, ?????? ????????? ???????????? ???????????????!',
            buttons: [
                {
                    text: '???????????????',
                    handler: () => {},
                },
            ],
        });
        await alert.present();
    }

    async getAddressWithLatLng(lat: number, lng: number) {
        const response = await this.bathroomService.getAddressName(lat, lng);

        if (response.data.code === 1000) {
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
            header: '???????????? ????????? ???????????? ???????????????.',
            message: '?????? ????????? ??????????????????!',
            duration: 4000,
        });
        await toast.present();
    }

    bathroomInformation() {
        if (!this.lat) {
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
            imageUrl: '',
        };

        return info;
    }

    onRatingChange(event) {
        this.rate = event.rate;
    }

    async onClickSaveButton() {
        if (!this.checkAddValidation()) {
            this.failValidationToast();
            return;
        }

        const loading = await this.loadingController.create({
            message: '????????? ?????? ??????????????????...',
        });

        const info = this.bathroomInformation();
        const response = await this.bathroomService.addBathroom(info, this.imageList);

        await loading.dismiss();

        if (response.data.code === 1000) {
            this.successAddBathroom();
        } else {
            this.failAddBathroom();
        }
    }

    async successAddBathroom() {
        const toast = await this.toastController.create({
            message: '????????? ????????? ?????????????????????!',
            duration: 1500,
        });
        await toast.present();
        await this.modalController.dismiss();
    }

    async failAddBathroom() {
        const toast = await this.toastController.create({
            message: '????????? ?????? ??????',
            duration: 1500,
        });
        await toast.present();
    }

    checkAddValidation() {
        if (!this.bathroomName || !this.bathroomAddress) {
            this.isValid = false;
            return false;
        }

        this.isValid = true;
        return true;
    }

    async failValidationToast() {
        const toast = await this.toastController.create({
            message: '?????? ???????????? ??????????????????',
            duration: 1500,
        });
        await toast.present();
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
            const imageData = {
                fileName: undefined,
                fileFormat: undefined,
                fileBlob: undefined,
            };
            const displayImageData = {
                imageName: undefined,
                image: undefined,
            };

            const image = await Camera.getPhoto({
                quality: 30,
                allowEditing: false,
                resultType: CameraResultType.Uri,
                promptLabelHeader: '?????? ??????',
                promptLabelPhoto: '???????????? ??????',
                promptLabelPicture: '?????? ??????',
                promptLabelCancel: '??????',
            });

            const imagePath = this.userPlatform === 'web' ? image.webPath : image.path;
            const splitedImagePath = imagePath.split('/');
            const imageName = imagePath.split('/')[splitedImagePath.length - 1];
            const imageBase64 = await Filesystem.readFile({ path: imagePath });
            const blob = await this.imageService.b64toBlob(
                imageBase64.data,
                `image/${image.format}`,
            );
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

    async onFileChange(files) {
        const imageData = {
            fileName: undefined,
            fileFormat: undefined,
            fileBlob: undefined,
        };
        const displayImageData = {
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
                const alert = await this.alertController.create({
                    message: '.webp ????????? ????????? ????????? ????????? ??? ??? ????????????.',
                    buttons: [
                        {
                            text: '??????',
                            handler: () => {},
                        },
                    ],
                });
                await alert.present();

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

    async deleteImage(imageIndex) {
        const alert = await this.alertController.create({
            subHeader: '?????????????????????????',
            buttons: [
                {
                    text: '??????',
                    handler: () => {},
                },
                {
                    text: '??????',
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
