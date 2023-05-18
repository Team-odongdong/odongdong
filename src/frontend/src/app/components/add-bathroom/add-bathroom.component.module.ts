import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AddBathroomComponent } from './add-bathroom.component';
import { RatingComponentModule } from '../rating/rating.component.module';

@NgModule({
  imports: [CommonModule, FormsModule, IonicModule, RatingComponentModule],
  declarations: [AddBathroomComponent],
  exports: [AddBathroomComponent],
})
export class AddBathroomComponentModule {}
