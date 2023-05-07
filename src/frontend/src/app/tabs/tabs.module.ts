import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { TabsPageRoutingModule } from './tabs-routing.module';

import { TabsPage } from './tabs.page';
import { AddBathroomComponentModule } from '../modals/add-bathroom/add-bathroom.module';
import { AddBathroomComponent } from '../modals/add-bathroom/add-bathroom.component';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    TabsPageRoutingModule,
    AddBathroomComponentModule
  ],
  declarations: [TabsPage],
  entryComponents: [AddBathroomComponent]
})
export class TabsPageModule {}
