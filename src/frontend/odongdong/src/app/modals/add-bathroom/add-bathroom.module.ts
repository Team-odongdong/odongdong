import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AddBathroomComponent } from './add-bathroom.component';
import { RatingComponentComponentModule } from 'src/app/components/rating-component/rating-component.module';

@NgModule({
    imports: [CommonModule, FormsModule, IonicModule, RatingComponentComponentModule],
    declarations: [AddBathroomComponent],
    exports: [AddBathroomComponent],
})
export class AddBathroomComponentModule {}
