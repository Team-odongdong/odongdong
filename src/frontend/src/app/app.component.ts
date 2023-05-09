import { Component } from '@angular/core';
import { Platform } from '@ionic/angular';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent {
  public device = '';
  public os = '';

  public userPlatform = 'web';

  constructor(platform: Platform) {}
}
