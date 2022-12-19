import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NavController, NavParams, ToastController } from '@ionic/angular';
import { BathroomInfo } from 'src/app/entities/bathroom';
import { BathroomService } from 'src/app/services/bathroom/bathroom-service';

import * as dayjs from 'dayjs';
import { TimeService } from 'src/app/services/time/time-service';

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
    public is24hours = true;

    public inputStartTime: string;
    public inputEndTime: string;

    constructor(
        public router: Router,
        public navParams: NavParams,
        public navController: NavController,
        public activatedRoute: ActivatedRoute,
        public bathroomService: BathroomService,
        public toastController: ToastController,
        public timeService: TimeService,
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
        if (this.inputStartTime === undefined) {
            this.inputStartTime = this.timeService.formatTimeToHourAndMinute(dayjs().format());
        }
        if (this.inputEndTime === undefined) {
            this.inputEndTime = this.timeService.formatTimeToHourAndMinute(dayjs().format());
        }

        /* eslint-disable */
        const updatedBathroom: BathroomInfo = {
            'address': this.bathroomAddress,
            'addressDetail': this.bathroomAddressDetail,
            'bathroomId': this.bathroomInfo.id,
            'isLocked': this.isLocked,
            'isUnisex': this.isUnisex,
            'latitude': this.bathroomInfo.latitude,
            'longitude': this.bathroomInfo.longitude,
            'operationTime': this.is24hours
                ? '24시간'
                : this.inputStartTime + '~' + this.inputEndTime,
            'title': this.bathroomName,
            'rate': this.bathroomInfo.rate,
            'imageUrl': this.bathroomInfo.imageUrl,
        };
        /* eslint-enable */

        return updatedBathroom;
    }

    goBack() {
        this.navController.navigateBack('/tabs/main');
    }

    checkIs24hours() {
        this.is24hours = !this.is24hours;
    }

    checkIsUnisex() {
        this.isUnisex = !this.isUnisex;
    }

    checkIsLocked() {
        this.isLocked = this.isLocked === 'Y' ? 'N' : 'Y';
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

    onStartTimeChanged(time) {
        this.inputStartTime = time.time;
    }

    onEndTimeChanged(time) {
        this.inputEndTime = time.time;
    }
}
