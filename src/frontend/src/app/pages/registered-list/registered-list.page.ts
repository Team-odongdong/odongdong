import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { AuthService } from 'src/app/services/authService';
import { CommonService } from 'src/app/services/commonService';
import { BathroomDetailInfo } from 'src/app/types/bathroomInfo';

@Component({
  selector: 'app-registered-list',
  templateUrl: './registered-list.page.html',
  styleUrls: ['./registered-list.page.scss'],
})
export class RegisteredListPage implements OnInit {
  public registeredBathrooms: BathroomDetailInfo[];

  constructor(
    public navController: NavController,
    private auth: AuthService,
    private common: CommonService
  ) {}

  ngOnInit() {
    this.getBathroomInfo();
  }

  async getBathroomInfo() {
    const { data } = await this.auth.getProfile();

    if (data.code === 1000) {
      this.registeredBathrooms = data.result.registerBathrooms;
    } else {
      await this.common.showAlert('화장실 정보를 가져오지 못했습니다.');
    }

    // this.bathroomInfo = {
    //   address: '서울 마포구 큰우물로 75',
    //   addressDetail: '성지빌딩',
    //   id: 8992,
    //   imageUrl:
    //     'https://bucket-odongdong.s3.ap-northeast-2.amazonaws.com/default/default-img.png',
    //   isLocked: 'N',
    //   isUnisex: false,
    //   latitude: 37.5412654954538,
    //   longitude: 126.946294819719,
    //   operationTime: '24시간',
    //   rate: 0,
    //   title: '성지빌딩 화장실',
    //   isOpened: 'Y',
    //   distance: 0,
    // };
  }

  goBack() {
    this.navController.back();
  }
}
