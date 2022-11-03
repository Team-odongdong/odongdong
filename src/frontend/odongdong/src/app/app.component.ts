import { Component } from '@angular/core';

import { Platform } from '@ionic/angular';

import { CommonService } from './services/common/common-service';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent {
  public device;
  public os;

  public userPlatform: string;

  constructor(
    public platform: Platform,
    public commonService: CommonService,
  ) {
    this.initializeApp();
  }

  initializeApp() {
    if(!this.commonService.checkNetworkStatus()) {
      return;
    }

    this.platform.ready().then(async () => {
      await this.setPlatform();
      console.log('setting for', this.userPlatform+'...');
    })
  }

  async setPlatform() {
    if(this.platform.is("android")) {
      this.userPlatform = "md";
    } else if(this.platform.is("ios" || "ipad" || "iphone")) {
      this.userPlatform = "ios";
    } else {
      this.userPlatform = "web";
    }
  }

}
