import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisteredListPage } from './registered-list.page';

const routes: Routes = [
  {
    path: '',
    component: RegisteredListPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RegisteredListPageRoutingModule {}
