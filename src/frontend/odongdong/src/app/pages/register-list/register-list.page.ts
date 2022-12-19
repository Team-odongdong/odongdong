import { Component, OnInit } from '@angular/core';
import { BathroomInfo } from 'src/app/entities/bathroom';

@Component({
    selector: 'app-register-list',
    templateUrl: './register-list.page.html',
    styleUrls: ['./register-list.page.scss'],
})
export class RegisterListPage implements OnInit {
    public bathroomInfo: BathroomInfo;

    constructor() {}

    ngOnInit() {
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
        };
    }
}
