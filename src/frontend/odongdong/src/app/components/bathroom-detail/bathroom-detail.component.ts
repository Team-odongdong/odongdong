import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { NavigationExtras } from '@angular/router';
import { ModalController, NavController } from '@ionic/angular';
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
        public modalController: ModalController,
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
        // this.isUnisex = bathroonInfo.isUnisex;

        this.isUnisex = true;
    }

    onRatingChange(inputRate: number) {
        this.editedRate = inputRate;
    }

    extendDetail() {
        this.extended = true;
    }

    async editBathroom() {
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
}
