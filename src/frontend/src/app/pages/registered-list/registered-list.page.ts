import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { BathroomDetailInfo, BathroomInfo } from 'src/app/types/bathroomInfo';

@Component({
  selector: 'app-registered-list',
  templateUrl: './registered-list.page.html',
  styleUrls: ['./registered-list.page.scss'],
})
export class RegisteredListPage implements OnInit {
  public bathroomInfo: BathroomDetailInfo;

  constructor(public navController: NavController) {}

  ngOnInit() {
    this.getBathroomInfo();
  }

  getBathroomInfo() {
    this.bathroomInfo = {
      address: '서울 마포구 큰우물로 75',
      addressDetail: '성지빌딩',
      id: 8992,
      imageUrl:
        'https://bucket-odongdong.s3.ap-northeast-2.amazonaws.com/default/default-img.png',
      isLocked: 'N',
      isUnisex: false,
      latitude: 37.5412654954538,
      longitude: 126.946294819719,
      operationTime: '24시간',
      rate: 0,
      title: '성지빌딩 화장실',
      isOpened: 'Y',
      distance: 0,
    };
  }

  goBack() {
    this.navController.back();
  }
}
