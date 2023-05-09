import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';
import { CustomerServiceComponent } from './customer-service.component';

@NgModule({
  imports: [CommonModule, FormsModule, IonicModule],
  declarations: [CustomerServiceComponent],
  exports: [CustomerServiceComponent],
})
export class AddBathroomComponentModule {}
