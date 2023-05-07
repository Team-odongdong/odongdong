import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterListPage } from './register-list.page';

const routes: Routes = [
  {
    path: '',
    component: RegisterListPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RegisterListPageRoutingModule {}
