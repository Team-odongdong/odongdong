import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SettingPage } from './setting.page';

import { SettingPageRoutingModule } from './setting-routing.module';
import { CustomerServiceComponent } from 'src/app/modals/customer-service/customer-service.component';
import { PolicyComponent } from 'src/app/modals/policy/policy.component';
import { PolicyComponentModule } from 'src/app/modals/policy/policy.module';
import { CustomerServiceComponentModule } from 'src/app/modals/customer-service/customer-service.module';
import { RatingComponentComponentModule } from 'src/app/components/rating-component/rating-component.module';

@NgModule({
    imports: [
        IonicModule,
        CommonModule,
        FormsModule,
        SettingPageRoutingModule,
        PolicyComponentModule,
        CustomerServiceComponentModule,
        RatingComponentComponentModule,
    ],
    declarations: [SettingPage],
    entryComponents: [PolicyComponent, CustomerServiceComponent],
})
export class SettingPageModule {}
