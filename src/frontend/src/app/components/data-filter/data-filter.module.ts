import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { DataFilterComponent } from './data-filter.component';

@NgModule({
  imports: [CommonModule, FormsModule, IonicModule],
  declarations: [DataFilterComponent],
  exports: [DataFilterComponent],
})
export class DataFilterComponentModule {}
