import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule, NavParams } from '@ionic/angular';

import { EditBathroomPageRoutingModule } from './edit-bathroom-routing.module';

import { EditBathroomPage } from './edit-bathroom.page';
import { TimePickerComponent } from 'src/app/components/time-picker/time-picker.component';
import { TimePickerComponentModule } from 'src/app/components/time-picker/time-picker.module';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        TimePickerComponentModule,
        EditBathroomPageRoutingModule,
    ],
    providers: [NavParams],
    declarations: [EditBathroomPage],
    entryComponents: [TimePickerComponent],
})
export class EditBathroomPageModule {}
