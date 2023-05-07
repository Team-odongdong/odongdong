import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { BathroomDetailComponent } from './bathroom-detail.component';
import { RatingComponentComponentModule } from '../rating-component/rating-component.module';

@NgModule({
    imports: [CommonModule, FormsModule, IonicModule, RatingComponentComponentModule],
    declarations: [BathroomDetailComponent],
    exports: [BathroomDetailComponent],
})
export class BathroomDetailComponentModule {}
