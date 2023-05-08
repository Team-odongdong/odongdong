import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SettingPage } from './setting.page';

import { SettingPageRoutingModule } from './setting-routing.module';

@NgModule({
  imports: [IonicModule, CommonModule, FormsModule, SettingPageRoutingModule],
  declarations: [SettingPage],
})
export class SettingPageModule {}
