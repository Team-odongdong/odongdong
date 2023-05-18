import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MainPage } from './main.page';

import { MainPageRoutingModule } from './main-routing.module';
import { AddBathroomComponentModule } from 'src/app/components/add-bathroom/add-bathroom.component.module';
import { BathroomDetailComponentModule } from 'src/app/components/bathroom-detail/bathroom-detail.component.module';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    MainPageRoutingModule,
    AddBathroomComponentModule,
    BathroomDetailComponentModule,
  ],
  declarations: [MainPage],
})
export class MainPageModule {}
