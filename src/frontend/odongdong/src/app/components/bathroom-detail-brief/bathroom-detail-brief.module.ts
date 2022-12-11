import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { BathroomDetailBriefComponent } from './bathroom-detail-brief.component';
import { IonicRatingComponentModule } from 'ionic-rating-component';

@NgModule({
    imports: [CommonModule, FormsModule, IonicModule, IonicRatingComponentModule],
    declarations: [BathroomDetailBriefComponent],
    exports: [BathroomDetailBriefComponent],
})
export class BathroomDetailBriefComponentModule {}
