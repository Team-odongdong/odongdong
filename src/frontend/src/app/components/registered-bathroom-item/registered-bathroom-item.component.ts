import { Component, Input, OnInit } from '@angular/core';
import { NavigationExtras } from '@angular/router';
import { AlertController, NavController } from '@ionic/angular';
import { BathroomDetailInfo } from 'src/app/types/bathroomInfo';

@Component({
  selector: 'app-registered-bathroom-item',
  templateUrl: './registered-bathroom-item.component.html',
  styleUrls: ['./registered-bathroom-item.component.scss'],
})
export class RegisteredBathroomItemComponent implements OnInit {
  @Input() isRegistered: boolean;
  @Input() bathroomInfo: BathroomDetailInfo;

  public editImageUrl: string;
  public deleteImageUrl: string;

  public addressFull: string = '';

  constructor(
    public navController: NavController,
    public alertController: AlertController
  ) {}

  ngOnInit() {
    this.setImages();
    this.makeFullAddress();

    console.log('list', this.bathroomInfo);
  }

  setImages() {
    if (this.isRegistered) {
      this.editImageUrl = '../../../assets/svgs/edit-blue.svg';
      this.deleteImageUrl = '../../../assets/svgs/delete-gray.svg';
    } else {
      this.editImageUrl = '../../../assets/svgs/edit-gray.svg';
      this.deleteImageUrl = '../../../assets/svgs/delete-red.svg';
    }
  }

  makeFullAddress() {
    this.addressFull =
      this.bathroomInfo.address + ' ' + this.bathroomInfo.addressDetail;
  }

  editBathroom() {
    const props: NavigationExtras = {
      state: {
        bathroomInfo: this.bathroomInfo,
      },
    };
    this.navController.navigateForward(
      `/edit-bathroom/${this.bathroomInfo.id}`,
      props
    );
  }

  deleteBathroom() {
    this.showDeleteAlert();
  }

  async showDeleteAlert() {
    const alert = await this.alertController.create({
      header: '화장실을 삭제하시겠어요?',
      buttons: [
        {
          text: '삭제하기',
          handler: () => {
            console.log('삭제 확인');
          },
        },
        {
          text: '취소',
          handler: () => {
            console.log('취소');
          },
        },
      ],
    });
    await alert.present();
  }
}
