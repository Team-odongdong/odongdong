import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';
import { BathroomDetailComponent } from './bathroom-detail.component';

@NgModule({
  imports: [CommonModule, FormsModule, IonicModule],
  declarations: [BathroomDetailComponent],
  exports: [BathroomDetailComponent],
})
export class BathroomDetailComponentModule {}
