import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-bathroom-detail',
  templateUrl: './bathroom-detail.component.html',
  styleUrls: ['./bathroom-detail.component.scss'],
})
export class BathroomDetailComponent implements OnInit {
  @Input() bathroomInfo: any;
  
  public bathroomName: string;
  public rating: any;
  public isLocked: string;
  public imageUrl: string;
  public operationTime: string;
  public address: string;
  public isOpened: string;

  constructor() { }

  ngOnInit() {
    this.setBathroomDetailInfo();
  }
  
  setBathroomDetailInfo() {
    this.bathroomName = this.bathroomInfo.title;
    this.rating = this.bathroomInfo.rate;
    this.isLocked = this.bathroomInfo.isLocked;
    this.operationTime = this.bathroomInfo.operationTime;
    this.address = this.bathroomInfo.address;
    this.imageUrl = this.bathroomInfo.imageUrl;
    this.isOpened = this.bathroomInfo.isOpened;
  }

  onRatingChange(rating) {
    this.rating = rating;
  }  
}
