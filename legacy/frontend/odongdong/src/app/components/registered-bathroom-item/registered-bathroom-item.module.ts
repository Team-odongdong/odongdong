import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { RegisteredBathroomItemComponent } from './registered-bathroom-item.component';

@NgModule({
    imports: [CommonModule, FormsModule, IonicModule],
    declarations: [RegisteredBathroomItemComponent],
    exports: [RegisteredBathroomItemComponent],
})
export class RegisteredBathroomItemComponentModule {}
