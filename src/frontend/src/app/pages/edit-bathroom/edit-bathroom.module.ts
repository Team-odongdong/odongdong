import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule, NavParams } from '@ionic/angular';

import { EditBathroomPageRoutingModule } from './edit-bathroom-routing.module';

import { EditBathroomPage } from './edit-bathroom.page';
import { TimePickerComponentModule } from 'src/app/components/time-picker/time-picker.component.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    EditBathroomPageRoutingModule,
    TimePickerComponentModule,
  ],
  providers: [NavParams],
  declarations: [EditBathroomPage],
})
export class EditBathroomPageModule {}
