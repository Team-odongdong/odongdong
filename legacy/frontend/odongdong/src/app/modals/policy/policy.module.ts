import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";

import { IonicModule } from "@ionic/angular";

import { PolicyComponent } from "./policy.component";


@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
    ],
    declarations: [PolicyComponent],
    exports: [PolicyComponent],
})
export class PolicyComponentModule {}