import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { EditBathroomPageRoutingModule } from './edit-bathroom-routing.module';

import { EditBathroomPage } from './edit-bathroom.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    EditBathroomPageRoutingModule
  ],
  declarations: [EditBathroomPage]
})
export class EditBathroomPageModule {}
