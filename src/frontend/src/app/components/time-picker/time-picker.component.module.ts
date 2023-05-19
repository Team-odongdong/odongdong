import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';
import { TimePickerComponent } from './time-picker.component';

@NgModule({
  imports: [CommonModule, FormsModule, IonicModule],
  declarations: [TimePickerComponent],
  exports: [TimePickerComponent],
})
export class TimePickerComponentModule {}
