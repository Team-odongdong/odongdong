import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';
import { RatingComponent } from './rating.component';

@NgModule({
  imports: [CommonModule, FormsModule, IonicModule],
  declarations: [RatingComponent],
  exports: [RatingComponent],
})
export class RatingComponentModule {}
