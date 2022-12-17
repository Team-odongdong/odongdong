import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { EditBathroomComponent } from './edit-bathroom.component';
import { IonicRatingComponentModule } from 'ionic-rating-component';

@NgModule({
    imports: [CommonModule, FormsModule, IonicModule, IonicRatingComponentModule],
    declarations: [EditBathroomComponent],
    exports: [EditBathroomComponent],
})
export class EditBathroomComponentModule {}
