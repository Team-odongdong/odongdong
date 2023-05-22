import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { DistanceFilterComponent } from './distance-filter.component';

@NgModule({
  imports: [CommonModule, FormsModule, IonicModule],
  declarations: [DistanceFilterComponent],
  exports: [DistanceFilterComponent],
})
export class DistanceFilterComponentModule {}
