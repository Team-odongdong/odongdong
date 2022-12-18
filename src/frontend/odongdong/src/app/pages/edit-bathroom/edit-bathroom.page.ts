import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NavController, NavParams, ToastController } from '@ionic/angular';
import { BathroomInfo } from 'src/app/entities/bathroom';
import { BathroomService } from 'src/app/services/bathroom/bathroom-service';

@Component({
    selector: 'app-edit-bathroom',
    templateUrl: './edit-bathroom.page.html',
    styleUrls: ['./edit-bathroom.page.scss'],
})
export class EditBathroomPage implements OnInit {
    public bathroomInfo: BathroomInfo;

    public bathroomName: string;
    public latitude: number;
    public longitude: number;
    public bathroomAddress: string;
    public bathroomAddressDetail: string;
    public operationTime: string;
    public isUnisex: boolean;
    public isLocked: string;
    public rating: number;

    public isValid = true;

    constructor(
        public router: Router,
        public navParams: NavParams,
        public navController: NavController,
        public activatedRoute: ActivatedRoute,
        public bathroomService: BathroomService,
        public toastController: ToastController,
    ) {}

    ngOnInit() {
        this.activatedRoute.queryParams.subscribe(() => {
            try {
                const params = this.router.getCurrentNavigation().extras.state;
                this.bathroomInfo = params.bathroomInfo;

                this.setBathroomInfo();
            } catch (error) {
                this.navController.navigateBack('/tabs/main');
            }
        });
    }

    setBathroomInfo() {
        this.bathroomName = this.bathroomInfo.title;
        this.latitude = this.bathroomInfo.latitude;
        this.longitude = this.bathroomInfo.longitude;
        this.bathroomAddress = this.bathroomInfo.address;
        this.bathroomAddressDetail = this.bathroomInfo.addressDetail;
        this.isUnisex = this.bathroomInfo.isUnisex;
        this.isLocked = this.bathroomInfo.isLocked;
        this.rating = this.bathroomInfo.rate;
    }

    genBathroomInfo() {
        /* eslint-disable */
        const updatedBathroom: BathroomInfo = {
            'address': this.bathroomAddress,
            'addressDetail': this.bathroomAddressDetail,
            'bathroomId': this.bathroomInfo.bathroomId,
            'isLocked': this.isLocked,
            'isUnisex': this.isUnisex,
            'latitude': this.latitude,
            'longitude': this.longitude,
            'operationTime': this.operationTime,
            'title': this.bathroomName,
            'rate': this.rating,
            'imageUrl': this.bathroomInfo.imageUrl,
        };
        /* eslint-enable */

        return updatedBathroom;
    }

    goBack() {
        this.navController.navigateBack('/tabs/main');
    }

    checkIsUnisex() {
        this.isUnisex = !this.isUnisex;
        console.log(this.isUnisex);
    }

    checkIsLocked() {
        this.isLocked = this.isLocked === 'Y' ? 'N' : 'Y';
        console.log(this.isLocked);
    }

    onRatingChange(rating: number) {
        this.rating = rating;
    }

    async onClickSaveButton() {
        const data = this.genBathroomInfo();

        const response = await this.bathroomService.editBathroom(data);

        if (response.data.code === 1000) {
            await this.successEditBathroomToast();
        } else {
            await this.failEditBathroomToast();
        }

        this.navController.back();
    }

    async successEditBathroomToast() {
        const toast = await this.toastController.create({
            message: '화장실 수정을 요청했습니다!',
            duration: 1500,
        });
        await toast.present();
    }

    async failEditBathroomToast() {
        const toast = await this.toastController.create({
            message: '네트워크를 확인 후 다시 시도해주세요!',
            duration: 2000,
        });
        await toast.present();
    }
}
