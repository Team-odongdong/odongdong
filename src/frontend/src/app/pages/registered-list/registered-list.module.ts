import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { RegisteredListPageRoutingModule } from './registered-list-routing.module';

import { RegisteredListPage } from './registered-list.page';
import { RegisteredBathroomItemComponentModule } from 'src/app/components/registered-bathroom-item/registered-bathroom-item.component.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RegisteredListPageRoutingModule,
    RegisteredBathroomItemComponentModule,
  ],
  declarations: [RegisteredListPage],
})
export class RegisteredListPageModule {}
