import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MainPage } from './main.page';

import { MainPageRoutingModule } from './main-routing.module';

@NgModule({
  imports: [IonicModule, CommonModule, FormsModule, MainPageRoutingModule],
  declarations: [MainPage],
})
export class MainPageModule {}
