import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";

import { IonicModule } from "@ionic/angular";

import { SocialLoginComponent } from "./social-login.component";


@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
    ],
    declarations: [SocialLoginComponent],
    exports: [SocialLoginComponent],
})
export class PolicyComponentModule {}