import { Component, Input, OnInit } from '@angular/core';
import { NavigationExtras } from '@angular/router';
import {
  AlertController,
  ModalController,
  NavController,
  ToastController,
} from '@ionic/angular';
import { BathroomService } from 'src/app/services/bathroomService';
import { CommonService } from 'src/app/services/commonService';
import { BathroomDetailInfo } from 'src/app/types/bathroomInfo';
import { roundDistance } from 'src/app/utils/formatting';

@Component({
  selector: 'app-bathroom-detail',
  templateUrl: './bathroom-detail.component.html',
  styleUrls: ['./bathroom-detail.component.scss'],
})
export class BathroomDetailComponent implements OnInit {
  @Input() bathroomInfo: any;

  public bathroomInfoForDisplay: any;

  public bathroomId: number;
  public bathroomName: string;
  public rate = 0;
  public isLocked: string;
  public imageUrl: string;
  public operationTime: string;
  public address: string;
  public isOpened: string;
  public isUnisex = false;
  public distance: number;

  public extended = false;

  public editedRate = 0;

  constructor(
    public navController: NavController,
    public bathroomService: BathroomService,
    public common: CommonService,
    public modal: ModalController,
    public alert: AlertController,
    public toast: ToastController
  ) {}

  ngOnInit() {
    this.bathroomInfoForDisplay = this.refineBathroomInfo(this.bathroomInfo);
    this.setBathroomDetailInfo(this.bathroomInfoForDisplay);
  }

  refineBathroomInfo(info: any) {
    if (info.imageUrl === null || !info.imageUrl.length) {
      info.imageUrl = null;
    }

    if (info.rate === undefined || info.rate === null) {
      info.rate = 0;
    }

    return info;
  }

  setBathroomDetailInfo(bathroomInfo: BathroomDetailInfo) {
    this.bathroomId = bathroomInfo.id;
    this.bathroomName = bathroomInfo.title;
    this.rate = bathroomInfo.rate;
    this.isLocked = bathroomInfo.isLocked;
    this.operationTime = bathroomInfo.operationTime;
    this.address = bathroomInfo.address + ' ' + this.bathroomInfo.addressDetail;
    this.imageUrl = bathroomInfo.imageUrl;
    this.isOpened = bathroomInfo.isOpened;
    this.isUnisex = bathroomInfo.isUnisex;
    this.distance = roundDistance(bathroomInfo.distance);
  }

  onRatingChange(event: any) {
    this.showRatingEditConfirmAlert(event.rate);
  }

  extendDetail() {
    this.extended = true;
  }

  editBathroom() {
    this.common.closePresentModal();

    const props: NavigationExtras = {
      state: {
        bathroomInfo: this.bathroomInfoForDisplay,
      },
    };

    this.navController.navigateForward(
      `/edit-bathroom/${this.bathroomInfoForDisplay.id}`,
      props
    );
  }

  async editRating(inputRate: number) {
    try {
      const response = await this.bathroomService.registerRating(
        this.bathroomId,
        inputRate
      );

      if (response.data.code === 1000) {
        await this.common.showToast('별점을 수정했습니다!', 1500);
      } else {
        await this.common.showAlert(
          '네트워크 상태를 확인 후 다시 시도해주세요.',
          '별점 수정을 실패했습니다.'
        );
      }
    } catch (error) {
      this.common.showAlert(error as string);
    }
  }

  async showRatingEditConfirmAlert(inputRate: number) {
    const alert = await this.alert.create({
      message: '별점을 수정하시겠어요?',
      buttons: [
        {
          text: '수정하기',
          handler: () => {
            this.editRating(inputRate);
          },
        },
        {
          text: '취소',
          handler: () => {},
        },
      ],
    });
    await alert.present();
  }
}
