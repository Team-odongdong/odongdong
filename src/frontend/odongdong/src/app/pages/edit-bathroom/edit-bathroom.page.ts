import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NavController, NavParams } from '@ionic/angular';
import { BathroomInfo } from 'src/app/entities/bathroom';

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
        console.log(rating);
    }
}
