import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-bathroom-detail',
  templateUrl: './bathroom-detail.component.html',
  styleUrls: ['./bathroom-detail.component.scss'],
})
export class BathroomDetailComponent implements OnInit {
  
  public bathroomName;
  public rate;
  public isLocked;
  public images;
  public operationTime;
  public address;

  constructor() { }

  ngOnInit() {
    this.bathroomName = '공중화장실 가중나무공원';
    this.rate = 4;
    this.isLocked = 'N';
    this.operationTime = '07:00 ~ 22:00';
    this.address = '서울특별시 광진구 화양동 32-1';
  }

  onRatingChange(rating) {
    this.rate = rating;
    console.log('rating clicked', rating);    
  }  
}
