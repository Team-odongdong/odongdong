import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NavController } from '@ionic/angular';

import * as dayjs from 'dayjs';
import { BathroomService } from 'src/app/services/bathroomService';
import { CommonService } from 'src/app/services/commonService';
import { BathroomDetailInfo } from 'src/app/types/bathroomInfo';
import { timeToHourAndMinute } from 'src/app/utils/formatting';

@Component({
  selector: 'app-edit-bathroom',
  templateUrl: './edit-bathroom.page.html',
  styleUrls: ['./edit-bathroom.page.scss'],
})
export class EditBathroomPage implements OnInit {
  public bathroomInfo: BathroomDetailInfo;

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
    private router: Router,
    private common: CommonService,
    private navController: NavController,
    private activatedRoute: ActivatedRoute,
    public bathroomService: BathroomService
  ) {}

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe(() => {
      try {
        const params = this.router.getCurrentNavigation()?.extras.state;
        this.bathroomInfo = params?.['bathroomInfo'];

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
      this.inputStartTime = timeToHourAndMinute(dayjs().format()) ?? '';
    }
    if (this.inputEndTime === undefined) {
      this.inputEndTime = timeToHourAndMinute(dayjs().format()) ?? '';
    }

    const updatedBathroom: any = {
      address: this.bathroomAddress,
      addressDetail: this.bathroomAddressDetail,
      id: this.bathroomInfo.id,
      isLocked: this.isLocked,
      isUnisex: this.isUnisex,
      latitude: this.bathroomInfo.latitude,
      longitude: this.bathroomInfo.longitude,
      operationTime: this.is24hours
        ? '24시간'
        : this.inputStartTime + '~' + this.inputEndTime,
      title: this.bathroomName,
      rate: this.bathroomInfo.rate,
      imageUrl: this.bathroomInfo.imageUrl,
    };

    return updatedBathroom;
  }

  goBack() {
    this.navController.back();
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
    await this.common.showToast('화장실 수정을 요청했습니다!', 1500);
  }

  async failEditBathroomToast() {
    await this.common.showToast('네트워크를 확인 후 다시 시도해주세요!', 2000);
  }

  onStartTimeChanged(time: any) {
    this.inputStartTime = time.time;
  }

  onEndTimeChanged(time: any) {
    this.inputEndTime = time.time;
  }
}
