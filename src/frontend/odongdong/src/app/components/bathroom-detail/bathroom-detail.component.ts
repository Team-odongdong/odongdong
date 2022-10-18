import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-bathroom-detail',
  templateUrl: './bathroom-detail.component.html',
  styleUrls: ['./bathroom-detail.component.scss'],
})
export class BathroomDetailComponent implements OnInit {
  @Input() bathroomInfo: any;
  
  public bathroomName: string;
  public rate;
  public isLocked: string;
  public imageUrl: string;
  public operationTime: string;
  public address: string;

  constructor() { }

  ngOnInit() {
    // this.bathroomName = "name bathroom";
    // this.rate = 4;
    // this.isLocked = 'N';
    // this.operationTime = '07:00 ~ 22:00';
    // this.address = 'addr bahtron';
    // this.imageUrl = null;


    this.bathroomName = this.bathroomInfo.title;
    this.rate = 4; //서버 구현중
    this.isLocked = this.bathroomInfo.isLocked;
    this.operationTime = '07:00 ~ 22:00'; //서버 구현중
    this.address = this.bathroomInfo.address;
    this.imageUrl = this.bathroomInfo.address;

    console.log('bathroom detail compo', this.bathroomInfo);
    // console.log('img', this.bathroomInfo.imageUrl);
    
  }

  onRatingChange(rating) {
    this.rate = rating;
    console.log('rating clicked', rating);    
  }  
}
