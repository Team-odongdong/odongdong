import { Component } from '@angular/core';
import { Platform } from '@ionic/angular';

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
  ) {
    this.initializeApp();
  }

  initializeApp() {
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
