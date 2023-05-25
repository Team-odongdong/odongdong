import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { RegisteredListPageRoutingModule } from './registered-list-routing.module';

import { RegisteredListPage } from './registered-list.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RegisteredListPageRoutingModule
  ],
  declarations: [RegisteredListPage]
})
export class RegisteredListPageModule {}
