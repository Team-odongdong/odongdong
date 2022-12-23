import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { RatingComponentComponent } from './rating-component.component';

@NgModule({
    imports: [CommonModule, FormsModule, IonicModule],
    declarations: [RatingComponentComponent],
    exports: [RatingComponentComponent],
})
export class RatingComponentComponentModule {}
