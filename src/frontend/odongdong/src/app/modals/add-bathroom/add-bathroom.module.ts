import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";

import { IonicModule } from "@ionic/angular";

import { AddBathroomComponent } from "./add-bathroom.component";
import { IonicRatingComponentModule } from "ionic-rating-component";


@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        IonicRatingComponentModule,
    ],
    declarations: [AddBathroomComponent],
    exports: [AddBathroomComponent],
})
export class AddBathroomComponentModule {}