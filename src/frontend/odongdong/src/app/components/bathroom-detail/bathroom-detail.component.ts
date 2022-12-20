import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { NavigationExtras } from '@angular/router';
import { AlertController, ModalController, NavController, ToastController } from '@ionic/angular';
import { LoginService } from 'src/app/services/auth/login-service';
import { BathroomService } from 'src/app/services/bathroom/bathroom-service';
import { CommonService } from 'src/app/services/common/common-service';

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
    public rate: number;
    public isLocked: string;
    public imageUrl: string;
    public operationTime: string;
    public address: string;
    public isOpened: string;
    public isUnisex: boolean;

    public extended = false;

    public editedRate = 0;

    constructor(
        public navController: NavController,
        public commonService: CommonService,
        public loginService: LoginService,
        public bathroomService: BathroomService,
        public modalController: ModalController,
        public alertController: AlertController,
        public toastController: ToastController,
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

    setBathroomDetailInfo(bathroonInfo: any) {
        this.bathroomId = bathroonInfo.id;
        this.bathroomName = bathroonInfo.title;
        this.rate = bathroonInfo.rate;
        this.isLocked = bathroonInfo.isLocked;
        this.operationTime = bathroonInfo.operationTime;
        this.address = bathroonInfo.address + ' ' + this.bathroomInfo.addressDetail;
        this.imageUrl = bathroonInfo.imageUrl;
        this.isOpened = bathroonInfo.isOpened;
        this.isUnisex = bathroonInfo.isUnisex;
    }

    onRatingChange(inputRate: number) {
        if (this.editedRate > 0) {
            this.showRatingEditConfirmAlert();
        } else {
            this.editRating(inputRate);
        }
    }

    extendDetail() {
        this.extended = true;
    }

    async checkLogin() {
        if (await this.commonService.isLogin()) {
            this.editBathroom();
        } else {
            this.loginService.needLoginAlert(true);
        }
    }

    editBathroom() {
        this.commonService.closePresentModal();

        const props: NavigationExtras = {
            state: {
                bathroomInfo: this.bathroomInfoForDisplay,
            },
        };

        this.navController.navigateForward(
            `/edit-bathroom/${this.bathroomInfoForDisplay.id}`,
            props,
        );
    }

    async editRating(inputRate: number) {
        const response = await this.bathroomService.registerRating(this.bathroomId, inputRate);

        if (response.data.code === 1000) {
            this.successEditRating();
        } else if (response.data.code === 3000) {
            this.loginService.needLoginAlert(true);
        } else {
            this.failToEditRating();
        }
    }

    async showRatingEditConfirmAlert() {
        const alert = await this.alertController.create({
            message: '별점을 수정하시겠어요?',
            buttons: [
                {
                    text: '수정하기',
                    handler: () => {
                        console.log('수정 요청');
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

    async successEditRating() {
        const toast = await this.toastController.create({
            message: '별점을 수정했습니다!',
            duration: 1500,
        });
        await toast.present();
    }

    async failToEditRating() {
        const alert = await this.alertController.create({
            header: '별점 수정을 실패했습니다',
            message: '네트워크 상태를 확인 후 다시 시도해주세요.',
            buttons: [
                {
                    text: '닫기',
                    handler: () => {},
                },
            ],
        });
        await alert.present();
    }
}
