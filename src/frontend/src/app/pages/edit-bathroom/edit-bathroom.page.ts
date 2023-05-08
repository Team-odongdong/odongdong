import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NavController } from '@ionic/angular';

import * as dayjs from 'dayjs';
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
    private navController: NavController,
    private activatedRoute: ActivatedRoute
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
    this.latitude = this.bathroomInfo.lat;
    this.longitude = this.bathroomInfo.lng;
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

    const updatedBathroom: Partial<BathroomDetailInfo> = {
      address: this.bathroomAddress,
      addressDetail: this.bathroomAddressDetail,
      id: this.bathroomInfo.id,
      isLocked: this.isLocked,
      isUnisex: this.isUnisex,
      lat: this.bathroomInfo.lat,
      lng: this.bathroomInfo.lng,
      operationTime: this.is24hours
        ? '24시간'
        : this.inputStartTime + '~' + this.inputEndTime,
      title: this.bathroomName,
      rate: this.bathroomInfo.rate,
      imageUrl: this.bathroomInfo.imageUrl,
    };

    return updatedBathroom;
  }
}
