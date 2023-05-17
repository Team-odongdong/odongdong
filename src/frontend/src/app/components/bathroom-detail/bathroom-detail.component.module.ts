import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';
import { BathroomDetailComponent } from './bathroom-detail.component';
import { RatingComponent } from '../rating/rating.component';
import { RatingComponentModule } from '../rating/rating.component.module';

@NgModule({
  imports: [CommonModule, FormsModule, IonicModule, RatingComponentModule],
  declarations: [BathroomDetailComponent],
  exports: [BathroomDetailComponent],
})
export class BathroomDetailComponentModule {}
