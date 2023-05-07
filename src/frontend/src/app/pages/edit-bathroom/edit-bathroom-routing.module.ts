import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { EditBathroomPage } from './edit-bathroom.page';

const routes: Routes = [
  {
    path: '',
    component: EditBathroomPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EditBathroomPageRoutingModule {}
