import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MainPage } from './main.page';
import { MainPageRoutingModule } from './main-routing.module';
import { BathroomDetailComponent } from 'src/app/components/bathroom-detail/bathroom-detail.component';
import { BathroomDetailComponentModule } from 'src/app/components/bathroom-detail/bathroom-detail.module';
import { AddBathroomComponent } from 'src/app/modals/add-bathroom/add-bathroom.component';
import { AddBathroomComponentModule } from 'src/app/modals/add-bathroom/add-bathroom.module';

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
  entryComponents: [BathroomDetailComponent, AddBathroomComponent],
})
export class MainPageModule {}
