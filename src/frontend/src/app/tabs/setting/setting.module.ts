import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SettingPage } from './setting.page';

import { SettingPageRoutingModule } from './setting-routing.module';
import { PolicyComponentModule } from 'src/app/components/policy/policy.component.module';
import { CustomerServiceComponentModule } from 'src/app/components/customer-service/customer-service.component.module';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    SettingPageRoutingModule,
    PolicyComponentModule,
    CustomerServiceComponentModule,
  ],
  declarations: [SettingPage],
})
export class SettingPageModule {}
