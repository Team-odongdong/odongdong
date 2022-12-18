import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule, NavParams } from '@ionic/angular';

import { EditBathroomPageRoutingModule } from './edit-bathroom-routing.module';

import { EditBathroomPage } from './edit-bathroom.page';
import { IonicRatingComponent, IonicRatingComponentModule } from 'ionic-rating-component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        IonicRatingComponentModule,
        EditBathroomPageRoutingModule,
    ],
    providers: [NavParams],
    declarations: [EditBathroomPage],
    entryComponents: [IonicRatingComponent],
})
export class EditBathroomPageModule {}
