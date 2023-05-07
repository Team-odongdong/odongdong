import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { RegisterListPageRoutingModule } from './register-list-routing.module';

import { RegisterListPage } from './register-list.page';
import { RegisteredBathroomItemComponentModule } from 'src/app/components/registered-bathroom-item/registered-bathroom-item.module';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        RegisterListPageRoutingModule,
        RegisteredBathroomItemComponentModule,
    ],
    declarations: [RegisterListPage],
})
export class RegisterListPageModule {}
