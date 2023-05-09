import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MainPage } from './main.page';

import { MainPageRoutingModule } from './main-routing.module';
import { BathroomDetailComponent } from 'src/app/components/bathroom-detail/bathroom-detail.component';

@NgModule({
  imports: [IonicModule, CommonModule, FormsModule, MainPageRoutingModule],
  declarations: [MainPage, BathroomDetailComponent],
})
export class MainPageModule {}
